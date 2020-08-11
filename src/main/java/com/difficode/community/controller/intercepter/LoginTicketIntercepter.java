package com.difficode.community.controller.intercepter;

import com.difficode.community.entity.LoginTicket;
import com.difficode.community.entity.User;
import com.difficode.community.service.UserService;
import com.difficode.community.utils.CookieUtil;
import com.difficode.community.utils.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;


@Component
public class LoginTicketIntercepter implements HandlerInterceptor {

    @Autowired
    private CookieUtil cookieUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private HostHolder hostHolder;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ticket = cookieUtil.getValue(request,"ticket");
        if (ticket!=null){
            LoginTicket loginTicket = userService.getLoginTicket(ticket);
            if (loginTicket!=null&&loginTicket.getStatus()==0&&loginTicket.getExpired().after(new Date())){
                User loginUser = userService.getUserByUserId(loginTicket.getUserId());
                hostHolder.setUser(loginUser);
            }

        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        User loginUser = hostHolder.getUser();
        if (loginUser!=null&&modelAndView!=null){
            modelAndView.addObject("loginUser",loginUser);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHolder.remove();
    }
}
