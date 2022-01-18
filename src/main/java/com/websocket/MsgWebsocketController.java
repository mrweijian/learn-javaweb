package com.websocket;

import org.springframework.stereotype.Controller;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * @ClassName MsgWebsocketController
 * @Author weijian
 * @Date 2022/1/10
 */
@ServerEndpoint(value = "/message_websocket")
@Controller
public class MsgWebsocketController {

    @OnOpen
    public void onOpen(Session session) {
        // 先鉴权，如果鉴权通过则存储WebsocketSession，否则关闭连接，这里省略了鉴权的代码
        //WebSocketSupport.storageSession(session);
        System.out.println("session open. ID:" + session.getId());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        System.out.println("session close. ID:" + session.getId());
    }

    /**
     * 收到客户端消息后调用的方法
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        session.getBasicRemote().sendText("1111");
        System.out.println("get client msg. ID:" + session.getId() + ". msg:" + message);
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

}

