package com.difficode.community.config;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KaptchaConfig {
    @Bean
    public Producer kaptcha(){
        Properties properties = new Properties();
        properties.put("kaptcha.border","yes");
        properties.put("kaptcha.border.color","black");
        properties.put("kaptcha.image.width","200");
        properties.put("kaptcha.image.height","50");
        properties.put("kaptcha.textproducer.char.string","1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        properties.put("kaptcha.textproducer.char.length","4");
        properties.put("kaptcha.textproducer.char.space","2");
        properties.put("kaptcha.textproducer.font.size","40");
        properties.put("kaptcha.textproducer.font.color","blue");
        properties.put("kaptcha.noise.impl","com.google.code.kaptcha.impl.DefaultNoise");
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
