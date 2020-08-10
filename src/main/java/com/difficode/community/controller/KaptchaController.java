package com.difficode.community.controller;

import com.difficode.community.config.KaptchaConfig;
import com.google.code.kaptcha.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;


@Controller
public class KaptchaController {
    private static final Logger LOGGER = LoggerFactory.getLogger(KaptchaController.class);
    @Autowired
    KaptchaConfig kaptchaConfig;
    @RequestMapping(value = "/kaptcha" ,method = RequestMethod.GET)
    public void kaptcha(HttpServletResponse response,HttpSession session){
        Producer kapchaProducer = kaptchaConfig.kaptcha();
        String text = kapchaProducer.createText();
        session.setAttribute("kaptcha",text);
        BufferedImage image = kapchaProducer.createImage(text);
        response.setContentType("image/png");
        try {
            OutputStream os = response.getOutputStream();
            ImageIO.write(image,"png",os);
        } catch (IOException e) {
            LOGGER.error("获取验证码失败",e.getMessage());
        }

    }

}
