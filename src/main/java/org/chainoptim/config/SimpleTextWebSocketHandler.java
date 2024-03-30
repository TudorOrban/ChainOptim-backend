package org.chainoptim.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class SimpleTextWebSocketHandler extends TextWebSocketHandler {

    private final WebSocketMessagingService messagingService;

    public SimpleTextWebSocketHandler(WebSocketMessagingService messagingService) {
        this.messagingService = messagingService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        String userId = session.getAttributes().get("userId").toString();
        System.out.println("User connected: " + userId);
        messagingService.registerSession(userId, session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("Received message: " + message.getPayload());
        session.sendMessage(new TextMessage("Hello, " + message.getPayload() + "!"));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//        super.afterConnectionClosed(session, status);
        String userId = session.getAttributes().get("userId").toString();
        System.out.println("User disconnected: " + userId + " - " + status);
        messagingService.unregisterSession(userId);
    }
}
