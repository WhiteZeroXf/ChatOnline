package com.zero.springboot.config;

import org.springframework.stereotype.Component;

/**
 * @author Hxf
 * @version V1.0
 * @Title:
 * @Description: TODO
 * @date 2018/9/7 10:49
 */
@Component
public class Constant {

    public static String UPLOAD_PATH;
    public static final int FIRST_LOGIN = 0;//首次登录
    public static final int UPDATE_USER_LIST = 1;//更新在线列表
    public static final int SEND_MESSAGE = 2;//发送消息
    public static final int CLIENT_ERROR = 500;//客户端异常

}
