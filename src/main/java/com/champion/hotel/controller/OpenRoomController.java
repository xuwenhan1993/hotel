package com.champion.hotel.controller;

import com.champion.hotel.entity.Record;
import com.champion.hotel.entity.Room;
import com.champion.hotel.mapper.RoomMapper;
import com.champion.hotel.service.OpenRoomService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Controller
public class OpenRoomController {
    @Autowired
    RoomMapper roomMapper;

    @Autowired
    private OpenRoomService openRoomService;

    //查询所有员工返回列表页面
    @GetMapping("/openRooms")
    public String list(@RequestParam(value = "pn", defaultValue = "1") int pn,
                       @RequestParam(value = "roomId", required = false) String roomId, Model model){
        PageHelper.startPage(pn, 7);
        if (roomId != null) {
            roomId = roomId.trim();
        }
        List<Room> rooms = roomMapper.listRooms(roomId);
        PageInfo<Room> pageInfo = new PageInfo<>(rooms,5);
        //放在请求域中
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("roomId", roomId);
        // thymeleaf默认就会拼串
        // classpath:/templates/xxxx.html
        return "openRoom/list";
    }

    //来到员工添加页面
    @GetMapping({"/openRoom/{rid}", "/openRoom/whole/{rid}"})
    public String toAddPage(@PathVariable("rid") Integer rid, ModelMap modelMap, HttpServletRequest request){
        Room room = roomMapper.getRoomById(rid);
        modelMap.addAttribute("room", room);
        modelMap.addAttribute("currentDate", new Date());
        String page = "openRoom/add";
        if (request.getRequestURI().contains("whole")) {
            page = "openRoom/whole";
        }
        return page;
    }

    //员工添加
    //SpringMVC自动将请求参数和入参对象的属性进行一一绑定；要求请求参数的名字和javaBean入参的对象里面的属性名是一样的
    @PostMapping("/openRoom")
    public String addEmp(Record record){
        openRoomService.openRoom(record);
        return "redirect:/records/detail";
    }

    /*//来到修改页面，查出当前员工，在页面回显
    @GetMapping("/room/{id}")
    public String toEditPage(@PathVariable("id") Integer id,Model model){
        Room room = roomMapper.getRoomById(id);
        model.addAttribute("room",room);

        //回到修改页面(add是一个修改添加二合一的页面);
        return "room/add";
    }

    //员工修改；需要提交员工id；
    @PutMapping("/room")
    public String updateEmployee(Room room){
        roomMapper.update(room);
        return "redirect:/rooms";
    }

    //员工删除
    @DeleteMapping("/room/{id}")
    public String deleteEmployee(@PathVariable("id") Integer id){
        roomMapper.delete(id);
        return "redirect:/rooms";
    }*/



}
