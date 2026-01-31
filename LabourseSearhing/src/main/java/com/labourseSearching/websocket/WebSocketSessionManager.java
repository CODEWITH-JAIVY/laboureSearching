package com.labourseSearching.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketSessionManager {

    // customerId -> WebSocketSession
    private final Map<Long, WebSocketSession> sessionMap = new ConcurrentHashMap<>();

    public void addSession(Long customerId, WebSocketSession session) {
        sessionMap.put(customerId, session);
    }

    public void removeSession(Long customerId) {
        sessionMap.remove(customerId);
    }

    public WebSocketSession getSession(Long customerId) {
        return sessionMap.get(customerId);
    }
}