package com.example.chattingwithkafka;

import com.example.chattingwithkafka.model.KafkaConstants;
import com.example.chattingwithkafka.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "/kafka")
public class ChatController {

    int sessionNumber = 0;
    int cnt = 0;

    @Autowired
    private KafkaTemplate<String, Message> kafkaTemplate;

    private ChatRoomSessionCache chatRoomSessionCache;

    public ChatController() {
        this.chatRoomSessionCache = ChatRoomSessionCache.getInstance();
    }


    //kafka cluster에 메세지 전송
    @PostMapping(value = "/publish")
    public void sendMessage(@RequestBody Message message) {
        message.setTimestamp(LocalDateTime.now().toString());
        //key값 설정해서 kafka에 전송
        try {
            kafkaTemplate.send(KafkaConstants.KAFKA_TOPIC, message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //구독중인 유저에게 돌려주는 곳
    @MessageMapping("/sendMessage/{chatRoomId}")
    @SendTo("/topic/group/{chatRoomId}")
    public Message broadcastGroupMessage(@Payload Message message) {
        //그냥 이렇게 되는건가? key값 집어놓고 하는건가?
        System.out.println("message");
        return message;
    }
}
