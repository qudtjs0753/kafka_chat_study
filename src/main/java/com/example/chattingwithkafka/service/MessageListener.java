package com.example.chattingwithkafka.service;


import com.example.chattingwithkafka.model.KafkaConstants;
import com.example.chattingwithkafka.model.Message;
import com.example.chattingwithkafka.storage.ChatRoomSessionCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageListener {
    //방과 관련된 session 저장소가 필요해보임

    @Autowired
    private ChatRoomSessionCache chatRoomSessionCache;

    @Autowired
    SimpMessagingTemplate template;


    /**
     * template.convertAndSend will convert the message and send that to WebSocket topic.
     * template.convertAndSend가 message 받고 WebSocket topic으로 전송함
     * 그러면 구독중인 웹소켓 토픽을 읽고 전송하는 것을 controller에서 수행함
     */
    @KafkaListener(
            topics = KafkaConstants.KAFKA_TOPIC,
            groupId = KafkaConstants.GROUP_ID
    )
    public void listen(@Payload Message message) {
        String destination = "/topic/user/"+message.getAuthor();
        template.convertAndSend(destination, message);
    }
}
