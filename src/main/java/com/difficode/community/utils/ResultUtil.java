package com.difficode.community.utils;

import com.difficode.community.vo.Result;
import com.difficode.community.vo.ResultCode;

public class ResultUtil {
    public static <T> Result<T> success(T data){
        return new Result<T>(ResultCode.SUCCESS.getCode(),ResultCode.SUCCESS.getMessage(),data);
    }
    public static <T> Result<T> success(){
        return new Result<T>(ResultCode.SUCCESS.getCode(),ResultCode.SUCCESS.getMessage(),null);
    }
    public static <T> Result<T> failed(){
        return new Result<T>(ResultCode.FAIL.getCode(),ResultCode.FAIL.getMessage(),null);
    }
}
