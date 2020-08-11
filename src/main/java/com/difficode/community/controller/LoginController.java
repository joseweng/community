package com.difficode.community.controller;

import com.difficode.community.entity.LoginTicket;
import com.difficode.community.entity.User;
import com.difficode.community.mapper.LoginTicketMapper;
import com.difficode.community.service.UserService;
import com.difficode.community.utils.ExpiredTime;
import com.difficode.community.utils.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;


@Controller
public class LoginController implements ExpiredTime {
    @Autowired
    private UserService userService;
    @Autowired
    private UUIDUtil uuidUtil;
    @GetMapping("/login")
    public String toLogin(){
        return "site/login";
    }

    @PostMapping("/login")
    public String login(String username , String password, String kaptcha, boolean rememberme,
                        HttpSession session, Model model, HttpServletResponse response){
        String code = (String) session.getAttribute("kaptcha");
        if (StringUtils.isBlank(code) || StringUtils.isBlank(kaptcha) || !code.equalsIgnoreCase(kaptcha)){
            model.addAttribute("kaptchaMsg","验证码错误");
            return "site/login";
        }
        long expiredTime = rememberme==true?LONG_EXPIREDTIME:NORMAL_EXPIREDTIME;
        Map<String,Object> map = userService.login(username,password,expiredTime);
        if (map.containsKey("ticket")){
            Cookie cookie = new Cookie("ticket",map.get("ticket").toString());
            cookie.setPath("/community");
            cookie.setMaxAge((int) expiredTime);
            response.addCookie(cookie);
            return "redirect:/index";
        }else if(map.containsKey("usernameMsg")){
            model.addAttribute("usernameMsg","用户名错误");
            return "/site/login";
        }else {
            model.addAttribute("passwordMsg","密码错误");
            return "/site/login";
        }
    }

    @GetMapping("/logout")
    public String logout(@CookieValue("ticket") String ticket){
        userService.logout(ticket);
        return "redirect:/login";
    }
}
