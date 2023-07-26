//package com.example.chattingwithkafka;
//
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.CloseStatus;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//@Component
//public class CustomWebsocketHandler extends TextWebSocketHandler {
//
//    private ChatRoomSessionCache sessions;
//
//
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        // WebSocket 연결이 성립되면 세션을 저장합니다.
//        sessions.setSession(session.getId(), session);
//    }
//
//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        // WebSocket 클라이언트로부터 메시지를 받았을 때의 처리 로직
//        String clientId = session.getId();
//        String receivedMessage = message.getPayload();
//        // Kafka Producer를 통해 메시지를 Kafka에 발행(Publish)합니다.
//
//        // 이 때, clientId 등의 식별자를 활용하여 메시지를 특정 클라이언트에게만 보낼 수 있도록 구현할 수 있습니다.
//        // ...
//    }
//
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//        // WebSocket 클라이언트와 연결이 종료되면 세션을 제거합니다.
//        sessions.remove(session.getId());
//    }
//}
