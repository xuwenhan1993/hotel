package com.champion.hotel.controller;

import com.champion.hotel.constant.Constants;
import com.champion.hotel.entity.Record;
import com.champion.hotel.entity.Room;
import com.champion.hotel.mapper.RoomMapper;
import com.champion.hotel.service.OpenRoomService;
import com.champion.hotel.service.RecordService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class RecordController {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private RecordService recordService;

    //查询所有员工返回列表页面
    @GetMapping("/records/out")
    public String list(@RequestParam(value = "pn", defaultValue = "1") int pn,
                       @RequestParam(value = "roomId", required = false) String roomId, Model model){
        PageHelper.startPage(pn, 7);
        if (roomId != null) {
            roomId = roomId.trim();
        }
        List<Record> records = recordService.listRecords(roomId, Constants.OUT, null, null, "predictOutDateOrder");
        PageInfo<Record> pageInfo = new PageInfo<>(records, 5);

        //放在请求域中
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("roomId", roomId);
        // thymeleaf默认就会拼串
        // classpath:/templates/xxxx.html
        return "record/list";
    }

    //来到退房页面
    @GetMapping("/record/out/{id}")
    public String toEditPage(@PathVariable("id") Integer id,Model model){
        Record record = recordService.getRecordById(id);
        // 退押金
        record.setBackDeposit(record.getDeposit());

        // 多退少补
        record.setBackAccount(0);
        record.setMoreAccount(0);
        Date predictOutDate = record.getPredictOutDate();
        // 退房时间，按12点算
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 12);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        Date now = today.getTime();
        int days = 0;
        if (now.before(predictOutDate)) { // 退房费
            days = (int)((predictOutDate.getTime() - now.getTime())/(1000*60*60*24)) + 1;
            if (days > 0) {
                record.setBackAccount(days * record.getPrice());
            }
        } else if (now.after(predictOutDate)) { // 补缴房费
            days = (int)((now.getTime() - predictOutDate.getTime())/(1000*60*60*24));
            if (days > 0) {
                record.setMoreAccount(days * record.getPrice());
            }
        }
        record.setActualOutDate(new Date());
        model.addAttribute("record", record);
        //回到退房页面
        return "record/out";
    }

    //员工修改；需要提交员工id；
    @PostMapping("/record")
    public String outOfService(Record record){
        recordService.outOfService(record);
        return "redirect:/records/out";
    }


    @GetMapping("/records/update")
    public String listRecordsUpdate(@RequestParam(value = "pn", defaultValue = "1") int pn,
                 @RequestParam(value = "roomId", required = false) String roomId, Model model){
        PageHelper.startPage(pn, 7);
        if (roomId != null) {
            roomId = roomId.trim();
        }
        List<Record> records = recordService.listRecords(roomId, Constants.OUT, null, null, "predictOutDateOrder");
        PageInfo<Record> pageInfo = new PageInfo<>(records, 5);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("roomId", roomId);
        return "recordUpdate/list";
    }

    //来到续住页面
    @GetMapping("/record/updateXz/{id}")
    public String toXzPage(@PathVariable("id") Integer id, Model model){
        Record record = recordService.getRecordById(id);

        model.addAttribute("record", record);
        //回到退房页面
        return "recordUpdate/updateXz";
    }

    //续住
    @PostMapping("/record/updateXz")
    public String updateRecordXz(Record record, @RequestParam("suppleAccount") String suppleAccount){
        record.setState(Constants.IN);
        recordService.update(record, suppleAccount);
        return "redirect:/records/update";
    }

    //来到换房间页面
    @GetMapping("/record/updateHfj/{id}")
    public String toHfjPage(@PathVariable("id") Integer id, Model model){
        Record record = recordService.getRecordById(id);

        model.addAttribute("record", record);
        //回到退房页面
        return "recordUpdate/updateHfj";
    }

    @GetMapping("/checkRoom")
    @ResponseBody
    public Room checkRoom(@RequestParam("roomId") String roomId) {
        Room room = roomMapper.getRoomByRoomId(roomId);
        return room;
    }

    //换房间
    @PostMapping("/record/updateHfj")
    public String updateRecordHfj(Record record){
        recordService.changeRoom(record);
        return "redirect:/records/update";
    }


    @GetMapping("/records/detail")
    public String listRecordsDetail(@RequestParam(value = "pn", defaultValue = "1") int pn,
                                    @RequestParam(value = "name", required = false) String name,
                                    @RequestParam(value = "idCard", required = false) String idCard,
                                    Model model){
        PageHelper.startPage(pn, 7);
        List<Record> records = recordService.listRecords(null, null,
                processParameterLike(name), processParameterLike(idCard), null);
        PageInfo<Record> pageInfo = new PageInfo<Record>(records, 5);
        //放在请求域中
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("name", name);
        model.addAttribute("idCard", idCard);
        // thymeleaf默认就会拼串
        // classpath:/templates/xxxx.html
        return "recordDetail/list";
    }

    //来到详情
    @GetMapping("/record/detail/{id}")
    public String toRecordDetail(@PathVariable("id") Integer id, Model model){
        Record record = recordService.getRecordById(id);

        model.addAttribute("record", record);
        return "recordDetail/detail";
    }

    @GetMapping("/checkIdCard")
    @ResponseBody
    public boolean checkIdCard(@RequestParam("idCard") String idCard) {
        return recordService.checkIdCard(idCard);
    }

    private String processParameterLike(String param) {
        if (param != null) {
            param = param.trim();
        }
        if (!StringUtils.isEmpty(param)) {
            param = "%" + param +"%";
        }
        return param;
    }
}
