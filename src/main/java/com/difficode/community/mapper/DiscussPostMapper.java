package com.difficode.community.mapper;

import com.difficode.community.entity.DiscussPost;

import java.util.List;

public interface DiscussPostMapper {
    List<DiscussPost> getDiscussPostList();
    List<DiscussPost> getDiscussPostListPage(int pageNum,int pageSize);

    int countDiscussPost();
}
