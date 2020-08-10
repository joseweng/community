package com.difficode.community.controller;

import com.difficode.community.entity.DiscussPost;
import com.difficode.community.entity.User;
import com.difficode.community.service.DiscussPostService;
import com.difficode.community.service.UserService;
import com.difficode.community.vo.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Api(tags = "讨论区接口",description = "讨论区接口")
@Controller
public class DiscussPostController {
    @Autowired
    DiscussPostService discussPostService;
    @Autowired
    UserService userService;

    @ApiOperation(value = "查看所有讨论",notes = "查看所有讨论")
    @GetMapping("/index")
    public String getDiscussPostList(Model model,Page page){
        page.setTotalCount(discussPostService.countDiscussPost());
        page.setPageSize(10);
        List<DiscussPost> discussPostList = discussPostService.getDiscussPostListPage(page.getPageNum(),page.getPageSize());
        List<Map<String,Object>> mapList = new ArrayList<>();
        if (discussPostList!=null){
            for (DiscussPost discussPost:discussPostList){
                Map<String,Object> map = new HashMap<>();
                map.put("discussPost",discussPost);
                User user = userService.getUserByUserId(discussPost.getUserId());
                map.put("user",user);
                mapList.add(map);
            }
        }

        model.addAttribute("mapList",mapList);
        System.out.println(model.toString());
        return "index";
    }
}
