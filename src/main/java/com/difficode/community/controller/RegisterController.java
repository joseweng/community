package com.difficode.community.controller;

import com.difficode.community.entity.User;
import com.difficode.community.service.UserService;
import com.difficode.community.utils.ActivationCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Api(value = "注册接口",description = "注册接口")
@Controller
public class RegisterController implements ActivationCode {
    @Autowired
    private UserService userService;
    @ApiOperation("跳转到注册页面")
    @GetMapping("/register")
    public String toRegister(){
        return "site/register";
    }


    @ApiOperation("注册功能")
    @PostMapping("/register")
    public String register(Model model, User user){
        Map<String,Object> retMap = userService.register(user);
        if (retMap==null||retMap.isEmpty()){
            model.addAttribute("msg","注册成功，我们已经向您的邮箱发送了一份激活邮件，请尽快激活");
            model.addAttribute("target","/index");
            return "site/operate-result";
        }else{
            model.addAttribute("usernameMsg",retMap.get("usernameMsg"));
            model.addAttribute("passwordMsg",retMap.get("passwordMsg"));
            model.addAttribute("emailMsg",retMap.get("emailMsg"));
            return "site/register";
        }

    }
    @GetMapping("/activation/{id}/{code}")
    public String activation(Model model, @PathVariable("id") int id,@PathVariable("code") String code){
        int result = userService.activation(id,code);
        if (result == ACTIVATION_SUCCESS){
            model.addAttribute("msg","激活成功！");
            model.addAttribute("target","/login");

        }else if(result==ACTIVATION_REPEAT){
            model.addAttribute("msg","此帐号无需再次激活！");
            model.addAttribute("target","/index");

        }else {
            model.addAttribute("msg","激活失败！");
            model.addAttribute("target","/index");
        }
        return "site/operate-result";
    }
}
