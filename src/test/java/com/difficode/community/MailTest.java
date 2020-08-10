package com.difficode.community;

import com.difficode.community.utils.MailUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MailTest {
    @Autowired
    private MailUtil mailUtil;
    @Autowired
    private TemplateEngine templateEngine;
    @Test
    public void testSendMail(){
        Context context = new Context();
        context.setVariable("username","陈芷妍女士");
        context.setVariable("postman","difficode");
        String content = templateEngine.process("/mail/demo",context);
//        System.out.println(content);
        mailUtil.sendMail("601834227@qq.com","difficode给您的一封信",content);
    }
}
