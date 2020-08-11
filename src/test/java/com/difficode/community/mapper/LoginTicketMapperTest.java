package com.difficode.community.mapper;

import com.difficode.community.CommunityApplication;
import com.difficode.community.entity.LoginTicket;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;



@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CommunityApplication.class)
class LoginTicketMapperTest {
    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Test
    void getLoginTicketByTicket() {
        System.out.println(loginTicketMapper.getLoginTicketByTicket("abc"));
    }

    @Test
    void saveLoginTicket() {
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(101);
        loginTicket.setTicket("abc");
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis()+1000*60*10));
        loginTicketMapper.saveLoginTicket(loginTicket);
    }

    @Test
    void updateLoginTicketStatus() {
        System.out.println(loginTicketMapper.updateLoginTicketStatus("abc",1));
    }
}