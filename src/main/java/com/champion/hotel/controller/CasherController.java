package com.champion.hotel.controller;

import com.champion.hotel.entity.Casher;
import com.champion.hotel.mapper.CasherMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author xuwenhan
 * @version v1.0
 * @create 2020/8/7
 */
@Controller
public class CasherController {

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private CasherMapper casherMapper;

    @GetMapping("/casher")
    public String casher(@RequestParam(value = "pn", defaultValue = "1") int pn,
                         @RequestParam(value = "start", required = false) String start,
                         @RequestParam(value = "end", required = false) String end,
                         ModelMap modelMap) throws ParseException {
        Date startDate = getDate(start);
        Date endDate = getDate(end);
        if (endDate != null) {
            Calendar instance = Calendar.getInstance();
            instance.setTime(endDate);
            instance.add(Calendar.DAY_OF_MONTH, 1);
            endDate = instance.getTime();
        }
        PageHelper.startPage(pn, 7);
        List<Casher> list = casherMapper.getList("%Y-%m-%d", startDate, endDate);
        PageInfo<Casher> pageInfo = new PageInfo<>(list, 5);
        modelMap.addAttribute("pageInfo", pageInfo);
        modelMap.addAttribute("start", start);
        modelMap.addAttribute("end", end);
        return "casher/list";
    }

    private Date getDate(String dateString) throws ParseException {
        if (StringUtils.isEmpty(dateString)) {
            return null;
        }
        return SIMPLE_DATE_FORMAT.parse(dateString);
    }
}
