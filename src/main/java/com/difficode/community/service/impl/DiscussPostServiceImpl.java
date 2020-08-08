package com.difficode.community.service.impl;

import com.difficode.community.entity.DiscussPost;
import com.difficode.community.mapper.DiscussPostMapper;
import com.difficode.community.service.DiscussPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscussPostServiceImpl implements DiscussPostService {
    @Autowired
    DiscussPostMapper discussPostMapper;
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
}
