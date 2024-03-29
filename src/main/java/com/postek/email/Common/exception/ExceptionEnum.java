package com.postek.email.Common.exception;

public enum ExceptionEnum {

    //Bit 1-2： 大类，00=登录，
    //Bit 3-4: 子类：00=需要重新登录的情况，01=权限受限
    //Bit 5-6: 详细信息
    LOST_CONNECTION_TO_SERVER("999999", "服务失败"),

    USER_NOT_LOGIN("000001", "用户未登录"),
    USER_ACCOUNT_ID_INVALID("000003", "用户id获取失败"),
    USER_ACCOUNT_NOT_EXIST("000004","用户账户错误"),
    USER_ACCOUNT_LOGIN_FAIL("000005","用户名或者密码错误"),
    USER_KID_ACCOUNT_NOT_EXIST("000006","小童账户错误"),
    USER_PASSWORD_ERROR("000007","密码错误"),
    USER_CAPTCHA_ERROR("000008","用户登录图形验证码密码错误"),
    USER_MOBILE_CAPTCHA_ERROR("000009","用户登录短信验证码密码错误"),


    USER_NOT_AUHTORIZED("000102", "当前用户无权限"),
    USER_EXISTS ("000201", "此用户名已经被占用"),
    USER_REGISTER_FAIL("000202", "用户注册失败"),
    USER_NAME_INVALID("000203", "用户名格式错误"),
    USER_MOBILE_INVALID("000204", "手机号码格式错误"),

    CAPTCH_COOKIE_KEY_INVALID("000201", "图片验证码凭据错误"),
    CAPTCH_INVALID("000202", "图片验证码错误"),
    USER_PASSWORD_RESET_FAIL("000302", "用户重置密码失败"),

    BIND_KID_INVALID("010001","绑定对象错误"),
    BIND_SAVE_ERROR("010002","绑定错误"),
    BIND_USER_INVALID("010003","当前用户不能绑定此小童"),
    BIND_KID_STATUS_INVALID("010004","当前小童不支持绑定"),
    BIND_TOKEN_NOT_EXIST("010005","绑定凭据不存在"),
    BIND_TOKEN_EXPIRED("010006","绑定凭据已过期"),
    BIND_TOKEN_INVALID("010007","绑定凭据错误"),
    BIND_NOT_EXIST("010008","绑定关系不存在"),

    BIND_RELATION_ERROR("010009","绑定关系不合要求"),
    BIND_FRIEND_INVALID("010010","绑定朋友关系对象错误"),
    BIND_FRIEND_NOT_EXIST("010011","绑定朋友关系对象错误"),
    FILE_UPLOAD_FAIL("020001", "素材上传错误"),
    FILE_NOT_EXISTS("020002", "素材不存在"),
    FILE_UPLOAD_STORY_ERROR("020003", "故事错误"),
    CHANNELS_NOT_INITED("020101", "频道未初始化"),

    MESSAGE_NOT_EXIST("020102", "信息不存在"),
    MESSAGE_AUDIT_STATUS_ERROR("020103", "信息审核状态错误"),
    MESSAGE_ACTION_ERROR("020104", "信息操作符错误"),

    ADMIN_USER_ACCOUNT_NOT_EXIST("100001","用户账户错误"),
    ADMIN_USER_AUTORITIES_INVALID("100002","用户权限错误"),
    ADMIN_SOUND_CHANNEL_OCCUPIED("100010","频道被占用"),
    ADMIN_SOUND_CHANNEL_NOT_EXIST("100011","频道不存在"),
    ;






    private final String code;
    private final String desc;

    ExceptionEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static ExceptionEnum fromCode(String xcode) {
        for (ExceptionEnum ec : ExceptionEnum.values()) {
            if (xcode.equals(ec.code())) {
                return ec;
            }
        }
        return null;
    }

    public String code() {
        return code;
    }

    public String desc() {
        return desc;
    }

}