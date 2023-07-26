package com.example.chattingwithkafka;


import com.example.chattingwithkafka.model.KafkaConstants;
import com.example.chattingwithkafka.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageListener {
    //방과 관련된 session 저장소가 필요해보임
    private ChatRoomSessionCache chatRoomSessionCache;

    public MessageListener() {
        chatRoomSessionCache = ChatRoomSessionCache.getInstance();
    }

    @Autowired
    SimpMessagingTemplate template;

    @KafkaListener(
            topics = KafkaConstants.KAFKA_TOPIC,
            groupId = KafkaConstants.GROUP_ID
    )
    public void listen(Message message) {
        log.info("sending via kafka listener...");

        //1. 여기서 경로로 나눈다
        //2.
//        template.convertAndSend("/topic/group/", message);
    }
}
