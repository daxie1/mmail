package com.happymail.common.util;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * http service 返回
 * @author yu
 *
 */
public class ServiceResponse<T>
{

    private int status;
    private String msg;
    private T data;

    private ServiceResponse(int status){
        this.status = status;
    }
    private ServiceResponse(int status,T data){
        this.status = status;
        this.data = data;
    }

    private ServiceResponse(int status,String msg,T data){
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    private ServiceResponse(int status,String msg){
        this.status = status;
        this.msg = msg;
    }
    /**
     * 不序列化此方法
     * @return
     */
    @JSONField(serialize=false)
    public boolean isSuccess(){
        return this.status == ResponseCode.SUCCESS.getCode();
    }

    public int getStatus(){
        return status;
    }
    public T getData(){
        return data;
    }
    public String getMsg(){
        return msg;
    }


    public static <T> ServiceResponse<T> createBySuccess(){
        return new ServiceResponse<T>(ResponseCode.SUCCESS.getCode());
    }

    public static <T> ServiceResponse<T> createBySuccessMessage(String msg){
        return new ServiceResponse<T>(ResponseCode.SUCCESS.getCode(),msg);
    }

    public static <T> ServiceResponse<T> createBySuccess(T data){
        return new ServiceResponse<T>(ResponseCode.SUCCESS.getCode(),data);
    }

    public static <T> ServiceResponse<T> createBySuccess(String msg,T data){
        return new ServiceResponse<T>(ResponseCode.SUCCESS.getCode(),msg,data);
    }


    public static <T> ServiceResponse<T> createByError(){
        return new ServiceResponse<T>(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDesc());
    }


    public static <T> ServiceResponse<T> createByErrorMessage(String errorMessage){
        return new ServiceResponse<T>(ResponseCode.ERROR.getCode(),errorMessage);
    }

    public static <T> ServiceResponse<T> createByErrorCodeMessage(int errorCode,String errorMessage){
        return new ServiceResponse<T>(errorCode,errorMessage);
    }
}
