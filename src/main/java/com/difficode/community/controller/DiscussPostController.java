package com.difficode.community.controller;

import com.difficode.community.entity.DiscussPost;
import com.difficode.community.entity.User;
import com.difficode.community.service.DiscussPostService;
import com.difficode.community.service.UserService;
import com.difficode.community.utils.HostHolder;
import com.difficode.community.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/discuss")
public class DiscussPostController {

    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private DiscussPostService discussPostService;
    @Autowired
    private JsonUtil jsonUtil;
    @Autowired
    private UserService userService;
    @PostMapping("/add")
    @ResponseBody
    public String addDiscussPost(String title,String content){
        User user = hostHolder.getUser();
        if (user==null){
            return jsonUtil.getJson(403,"请先登录");
        }
        DiscussPost discussPost = new DiscussPost();
        discussPost.setUserId(user.getId());
        discussPost.setTitle(title);
        discussPost.setContent(content);
        discussPost.setCreateTime(new Date());
        discussPostService.saveDiscussPost(discussPost);

        return jsonUtil.getJson(0,"发布成功");
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") int id , Model model){
        DiscussPost discussPost = discussPostService.getDiscussPostById(id);
        model.addAttribute("discussPost",discussPost);
        User user = userService.getUserByUserId(discussPost.getUserId());
        model.addAttribute("user",user);
        return "site/discuss-detail";
    }
}

