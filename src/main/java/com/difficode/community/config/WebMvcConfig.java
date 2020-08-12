package com.difficode.community.config;

import com.difficode.community.controller.intercepter.LoginRequiredIntercepter;
import com.difficode.community.controller.intercepter.LoginTicketIntercepter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private LoginTicketIntercepter loginTicketIntercepter;
    @Autowired
    private LoginRequiredIntercepter loginRequiredIntercepter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginTicketIntercepter)
                .excludePathPatterns("/**/*.css","/**/*.png","/**/*.jpg","/**/*.jpeg","/**/*.js");

        registry.addInterceptor(loginRequiredIntercepter)
                .excludePathPatterns("/**/*.css","/**/*.png","/**/*.jpg","/**/*.jpeg","/**/*.js");
    }
}
