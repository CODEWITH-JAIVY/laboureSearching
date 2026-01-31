package com.labourseSearching.websocket;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Service
public class WebSocketNotificationService {

    private final WebSocketSessionManager sessionManager;

    public WebSocketNotificationService(WebSocketSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public void notifyCustomer(Long customerId, String message) {
        try {
            WebSocketSession session = sessionManager.getSession(customerId);
            if (session != null && session.isOpen()) {
                session.sendMessage(new TextMessage(message));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}