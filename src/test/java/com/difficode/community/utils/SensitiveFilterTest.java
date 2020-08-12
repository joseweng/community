package com.difficode.community.utils;

import com.difficode.community.CommunityApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CommunityApplication.class)
public class SensitiveFilterTest {
    @Autowired
    private SensitiveFilter sensitiveFilter;
    @Test
    public void filter() {
        String text = "吸毒，嫖娼，哈哈，呵呵，赌博！";
        System.out.println(sensitiveFilter.filter(text));;
    }
}