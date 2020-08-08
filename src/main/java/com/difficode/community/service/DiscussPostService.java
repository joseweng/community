package com.difficode.community.service;

import com.difficode.community.entity.DiscussPost;

import java.util.List;

public interface DiscussPostService {
    List<DiscussPost> getDiscussPostList();
    List<DiscussPost> getDiscussPostListPage(int pageNum,int pageSize);
    int countDiscussPost();
}
