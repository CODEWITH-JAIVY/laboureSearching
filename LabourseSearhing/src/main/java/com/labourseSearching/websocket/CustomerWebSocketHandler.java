package com.labourseSearching.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.net.URI;
import java.util.Map;

@Component
public class CustomerWebSocketHandler extends TextWebSocketHandler {

    private final WebSocketSessionManager sessionManager;

    public CustomerWebSocketHandler(WebSocketSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        Long customerId = extractCustomerId(session);
        sessionManager.addSession(customerId, session);
        System.out.println("Customer connected via WS: " + customerId);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        Long customerId = extractCustomerId(session);
        sessionManager.removeSession(customerId);
        System.out.println("Customer disconnected: " + customerId);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        System.out.println("Received message: " + message.getPayload());
    }

    private Long extractCustomerId(WebSocketSession session) {
        URI uri = session.getUri();
        if (uri == null) return null;

        String query = uri.getQuery(); // customerId=123
        if (query == null) return null;

        for (String param : query.split("&")) {
            if (param.startsWith("customerId=")) {
                return Long.parseLong(param.split("=")[1]);
            }
        }
        return null;
    }
}