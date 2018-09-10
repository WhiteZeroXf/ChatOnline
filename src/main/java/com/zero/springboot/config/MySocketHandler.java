package com.zero.springboot.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zero.springboot.pojo.Message;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;

/**
 * @author Hxf
 * @version V1.0
 * @Title:
 * @Description: TODO
 * @date 2018/9/7 11:31
 */
public class MySocketHandler extends TextWebSocketHandler {

    private Map<String, WebSocketSession> users;
    private List<String> userList;

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String userId = Objects.requireNonNull(session.getRemoteAddress()).toString();
        users.remove(userId);
        userList.remove(userId);
        updateUserList();
    }

    MySocketHandler() {
        this.users = new LinkedHashMap<>();
        this.userList = new LinkedList<>();
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        deliverJob(session, message);

    }

    private void deliverJob(WebSocketSession session, TextMessage message) throws IOException {
        String jsonBody = new String(message.asBytes());
        JSONObject json;
        if (jsonBody.equals("") || jsonBody.equals("{}") || jsonBody.equals("undefined")) {
            json = new JSONObject();
            json.put("type", Constant.CLIENT_ERROR);
            session.close();
            return;
        }
        json = JSON.parseObject(jsonBody);
        switch ((int) json.get("type")) {
            case Constant.FIRST_LOGIN:
                firstLogin(session);
                break;
            case Constant.UPDATE_USER_LIST:
                //TODO
                break;
            case Constant.SEND_MESSAGE:
                sendMessage(session, json);
                break;
            default:
                System.out.println("error: " + json);
        }
    }

    private void firstLogin(WebSocketSession session) throws IOException {
        Map<String, Object> map = new HashMap<>();
        String userId = Objects.requireNonNull(session.getRemoteAddress()).toString();
        map.put("userList", userList);
        Message message = new Message();
        message.setType(Constant.UPDATE_USER_LIST);
        message.setData(map);
        users.put(userId, session);
        userList.add(userId);
        TextMessage textMessage = new TextMessage(JSON.toJSONString(message));
        session.sendMessage(textMessage);
        updateUserList();
    }

    private void updateUserList() throws IOException {
        for (String address : userList) {
            Map<String, Object> map = new HashMap<>();
            List<String> exceptMe = new ArrayList<>();
            exceptMe.addAll(userList);
            exceptMe.remove(address);
            map.put("userList", exceptMe);
            Message message = new Message();
            message.setType(Constant.UPDATE_USER_LIST);
            message.setData(map);
            TextMessage textMessage = new TextMessage(JSON.toJSONString(message));
            users.get(address).sendMessage(textMessage);
        }

    }

    private void sendMessage(WebSocketSession session, JSONObject json) throws IOException {
        session.sendMessage(new TextMessage("{\"type\":200}"));
        Message message = JSON.toJavaObject(json, Message.class);
        message.setFrom(Objects.requireNonNull(session.getRemoteAddress()).toString());
        WebSocketSession userTo = users.get(message.getTo());
        if (userTo != null) {
            userTo.sendMessage(new TextMessage(JSON.toJSONString(message)));
            return ;
        }
        session.sendMessage(new TextMessage("{\"type\":404}"));
    }

    private void printSessionInfo(WebSocketSession session) {
        System.out.println("--------------------------------");
        System.out.println("SessionId : " + session.getId());
        System.out.println("RemoteAddress : " + session.getRemoteAddress());
        System.out.println("--------------------------------");
    }

    private String getMessage(Message message) {
        return JSON.toJSONString(message);
    }

}

