package com.difficode.community.service.impl;

import com.difficode.community.entity.DiscussPost;
import com.difficode.community.mapper.DiscussPostMapper;
import com.difficode.community.service.DiscussPostService;
import com.difficode.community.utils.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
public class DiscussPostServiceImpl implements DiscussPostService {
    @Autowired
    DiscussPostMapper discussPostMapper;
    @Autowired
    private SensitiveFilter sensitiveFilter;
    @Override
    public List<DiscussPost> getDiscussPostList() {
        return discussPostMapper.getDiscussPostList();
    }

    @Override
    public List<DiscussPost> getDiscussPostListPage(int pageNum, int pageSize) {
        return discussPostMapper.getDiscussPostListPage(pageNum,pageSize);
    }

    @Override
    public int countDiscussPost() {
        return discussPostMapper.countDiscussPost();
    }

    @Override
    public int saveDiscussPost(DiscussPost discussPost) {
        if (discussPost==null){
            throw new IllegalArgumentException("参数不能为空");
        }

        discussPost.setTitle(HtmlUtils.htmlEscape(discussPost.getTitle()));
        discussPost.setContent(HtmlUtils.htmlEscape(discussPost.getContent()));

        discussPost.setTitle(sensitiveFilter.filter(discussPost.getTitle()));
        discussPost.setContent(sensitiveFilter.filter(discussPost.getContent()));

        return discussPostMapper.saveDiscussPost(discussPost);
    }

    @Override
    public DiscussPost getDiscussPostById(int id) {
        return discussPostMapper.getDiscussPostById(id);
    }
}
