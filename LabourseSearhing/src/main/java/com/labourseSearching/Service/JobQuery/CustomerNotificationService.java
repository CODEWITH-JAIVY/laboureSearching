package com.labourseSearching.Service.JobQuery;

import com.labourseSearching.websocket.WebSocketSessionManager;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Service
public class CustomerNotificationService {

    private final WebSocketSessionManager sessionManager;

    public CustomerNotificationService(WebSocketSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public void notifyCustomer(Long customerId, String message) {

        WebSocketSession session = sessionManager.getSession(customerId);

        if (session != null && session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}