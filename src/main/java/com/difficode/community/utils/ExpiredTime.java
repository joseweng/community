package com.difficode.community.utils;

import java.util.Date;

public interface ExpiredTime {
   long NORMAL_EXPIREDTIME=System.currentTimeMillis()+1000*3600*12;
   long LONG_EXPIREDTIME=System.currentTimeMillis()+1000*3600*24*15;

}
