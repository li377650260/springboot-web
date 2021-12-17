package com.tonpower.springbootweb.vo;

public enum  ErrorCode {

    PARAMS_ERROR(10001,"请输入用户名和密码"),
    ACCOUNT_PWD_NOT_EXIST(10002,"用户名或密码有误"),
    ACCOUNT_EXIST(10005,"此用户已经存在"),
    TOKEN_ERROR(10003,"登陆的用户不合法"),
    INTERFACE_CLOSED(10004,"此接口暂不开放"),
    UPLOAD_ERROR(10005,"图片上传失败"),
    NO_PERMISSION(70001,"无访问权限"),
    SESSION_TIME_OUT(90001,"会话超时"),
    NO_LOGIN(90002,"未登录"),;

    private int code;
    private String msg;

    ErrorCode(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}