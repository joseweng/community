package com.difficode.community.service.impl;

import com.difficode.community.entity.LoginTicket;
import com.difficode.community.entity.User;
import com.difficode.community.mapper.LoginTicketMapper;
import com.difficode.community.mapper.UserMapper;
import com.difficode.community.service.UserService;
import com.difficode.community.utils.ActivationCode;
import com.difficode.community.utils.MD5Util;
import com.difficode.community.utils.MailUtil;
import com.difficode.community.utils.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService , ActivationCode {
    @Autowired
    UserMapper userMapper;
    @Autowired
    private MailUtil mailUtil;
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private UUIDUtil uuidUtil;
    @Autowired
    private MD5Util md5Util;
    @Value("${community.path.domain}")
    private String domain;
    @Value("${server.servlet.context-path}")
    private String contextPath;
    @Autowired
    private LoginTicketMapper loginTicketMapper;
    /*
    * get user by userId*/
    @Override
    public User getUserByUserId(int userId) {
        return userMapper.getUserByUserId(userId);
    }

    /*
    * check register
    * */
    public Map<String,Object> register(User user){
        Map<String,Object> msgMap = new HashMap<>();
        if (user==null){
            throw  new IllegalArgumentException("参数不能为空");
        }
        if(StringUtils.isBlank(user.getUsername())){
            msgMap.put("usernameMsg","用户名不能为空");
            return msgMap;
        }
        if(StringUtils.isBlank(user.getPassword())){
            msgMap.put("passwordMsg","密码不能为空");
            return msgMap;
        }
        if(StringUtils.isBlank(user.getEmail())){
            msgMap.put("emailMsg","邮箱不能为空");
            return msgMap;
        }
        User realUser = userMapper.getUserByUsername(user.getUsername());
        if (realUser!=null){
            msgMap.put("usernameMsg","用户名已存在");
            return msgMap;
        }
        realUser = userMapper.getUserByEmail(user.getEmail());
        if (realUser!=null){
            msgMap.put("emailMsg","邮箱已被注册");
            return msgMap;
        }
        user.setSalt(uuidUtil.genericUUID().substring(0,5));
        user.setPassword(md5Util.md5(user.getPassword()+user.getSalt()));
        user.setType(0);
        user.setStatus(0);
        user.setActivationCode(uuidUtil.genericUUID());
        user.setHeaderUrl(String.format("http://images.nowcoder.com/head/%dt.png",new Random().nextInt(1000)));
        user.setCreateTime(new Date());
        userMapper.saveUser(user);
        String url = domain+contextPath+"/activation/"+user.getId()+"/"+user.getActivationCode();
        Context context = new Context();
        context.setVariable("email",user.getEmail());
        context.setVariable("url",url);
        String content = templateEngine.process("/mail/activation",context);
        mailUtil.sendMail(user.getEmail(),"激活账号",content);
        return  msgMap;
        }


    /*
    * check activation
    * */
    @Override
    public int activation(int id, String code) {
        User realUser = userMapper.getUserByUserId(id);
        if (realUser.getStatus()==1){
            return ACTIVATION_REPEAT;
        }else if(code.equals(realUser.getActivationCode())){
            userMapper.updateStatus(id,1);
            return ACTIVATION_SUCCESS;
        }else {
            return ACTIVATION_FAIL;
        }

    }

    /*
    * check login
    * */
    public Map<String ,Object> login(String username,String password,long expiredTime){
        Map<String,Object> map = new HashMap<>();
        if (StringUtils.isBlank(username)){
            map.put("usernameMsg","用户名不能为空");
            return map;
        }
        if (StringUtils.isBlank(password)){
            map.put("passwordMsg","密码不能为空");
            return map;
        }
        User realUser = userMapper.getUserByUsername(username);
        if (realUser==null){
            map.put("usernameMsg","用户不存在");
            return map;
        }
        String realPwd = md5Util.md5(password+realUser.getSalt());
        if (!StringUtils.equals(realPwd,realUser.getPassword())){
            map.put("passwordMsg","密码错误");
            return map;
        }
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(realUser.getId());
        loginTicket.setTicket(uuidUtil.genericUUID());
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(expiredTime));
        loginTicketMapper.saveLoginTicket(loginTicket);
        map.put("ticket",loginTicket.getTicket());
        return map;
    }

    public void logout(String ticket){
        loginTicketMapper.updateLoginTicketStatus(ticket,1);
    }


}
