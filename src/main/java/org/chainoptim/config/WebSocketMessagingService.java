package org.chainoptim.config;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WebSocketMessagingService {

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    public void registerSession(String userId, WebSocketSession session) {
        System.out.println("Registering session for user: " + userId);
        sessions.put(userId, session);
    }

    public void unregisterSession(String userId) {
        sessions.remove(userId);
    }

    public void sendMessageToUser(String userId, String message) {
        System.out.println("Sending message to user: " + userId);
        WebSocketSession session = sessions.get(userId);
        if (session != null && session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
