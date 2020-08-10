package com.difficode.community.utils;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UUIDUtil {
    public String genericUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
