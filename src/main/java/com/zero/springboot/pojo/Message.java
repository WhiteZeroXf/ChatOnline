package com.zero.springboot.pojo;

import lombok.Data;

/**
 * @author Hxf
 * @version V1.0
 * @Title:
 * @Description: TODO
 * @date 2018/9/10 14:20
 */
@Data
public class Message {

    private String token;
    private Integer type;
    private Object data;
    private String to;
    private String from;
    private String text;

}
