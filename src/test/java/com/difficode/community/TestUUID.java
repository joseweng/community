package com.difficode.community;


import com.difficode.community.utils.UUIDUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CommunityApplication.class)
public class TestUUID {
    @Autowired
    private UUIDUtil uuidUtil;
    @Test
    public void testUUID(){
        System.out.println(uuidUtil.genericUUID());
    }
}
