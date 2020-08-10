package com.champion.hotel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author xuwenhan
 * @version v1.0
 * @create 2020/6/1
 */
@Controller
public class LoginController {

    @PostMapping("/user/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password
            , ModelMap modelMap, HttpSession session) {
        if ("admin".equals(username) && "393939".equals(password)) {
            // 登录成功
            session.setAttribute("loginUser", username);
            return "redirect:/openRooms";
        }
        // 登录失败
        modelMap.addAttribute("msg", "登录失败，用户名或密码错误！");
        return "login";
    }

    @RequestMapping("//loginOut")
    public String loginout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "login";
    }
}
