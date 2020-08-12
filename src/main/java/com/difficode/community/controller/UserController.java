package com.difficode.community.controller;

import com.difficode.community.annotation.LoginRequired;
import com.difficode.community.entity.User;
import com.difficode.community.service.UserService;
import com.difficode.community.utils.HostHolder;
import com.difficode.community.utils.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@Controller
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UUIDUtil uuidUtil;
    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Value("${community.path.uploadPath}")
    private String uploadPath;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private UserService userService;


    @LoginRequired
    @GetMapping("/setting")
    public String getUserSetting(){
        return "site/setting";
    }


    @LoginRequired
    @PostMapping("/upload")
    public String updateHeader(MultipartFile headerImage, Model model){
        if (headerImage==null){
            model.addAttribute("error","没有选择图片");
            return "site/setting";
        }
        String fileName = headerImage.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        if (StringUtils.isBlank(suffix)){
            model.addAttribute("error","图片格式不正确");
            return "site/setting";
        }
        fileName = uuidUtil.genericUUID()+suffix;
        File dest = new File(uploadPath+"/"+fileName);
        try {
            headerImage.transferTo(dest);
        } catch (IOException e) {
            logger.error("error","文件上传失败");
            throw new RuntimeException("文件上传失败");
        }

        User user = hostHolder.getUser();
        String headerUrl = domain+contextPath+"/user/header/"+fileName;
        userService.updateHeaderUrl(user.getId(),headerUrl);
        return "redirect:/index";

    }

    @GetMapping("/header/{fileName}")
    public void getHeader(@PathVariable("fileName") String fileName, HttpServletResponse response){
        File dest = new File(uploadPath+"/"+fileName);
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        response.setContentType("image/"+suffix);
        try (
             OutputStream os = response.getOutputStream();
             FileInputStream fis = new FileInputStream(dest);
             ){

            byte[] bytes = new byte[1024];
            int b = 0;
            while ((b= fis.read(bytes))!=-1){
                os.write(bytes,0,b);
            }
        } catch (IOException e) {
            logger.error("error","读取头像失败");
        }
    }

}
