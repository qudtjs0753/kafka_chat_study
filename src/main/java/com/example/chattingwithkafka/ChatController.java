package com.example.chattingwithkafka;

import com.example.chattingwithkafka.model.KafkaConstants;
import com.example.chattingwithkafka.model.Message;
import lombok.extern.slf4j.Slf4j;
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
public class ChatController {

    @Autowired
    private ChatRoomSessionCache chatRoomSessionCache;


    //1. 방을 만든다 -> 대화하기 누르면 서버에 의해 세션 방 만들어짐(DB에 어느 유저가 있는지 저장) -> 이후 세션 값 반환
    //2. 웹소켓 연결 요청을 보낸다 -> 돌려받은 세션 값을 토대로 수행
    //3. 요청을 기반으로 소켓 연결을 수행한다
    //4. 소켓연결을 마치고나면 통신을 수행. 이때 WebSocket 구독은 roomID를 기반으로한다
    @PostMapping(value = "/create-room")
    public void createRoom() {
        chatRoomSessionCache.setSession("a","0");
        chatRoomSessionCache.setSession("b","1");
        chatRoomSessionCache.setSession("c","0");
        chatRoomSessionCache.setSession("d","1");
    }

    @Autowired
    private KafkaTemplate<String, Message> kafkaTemplate;

    //kafka cluster에 메세지 전송
    @PostMapping(value = "kafka/publish")
    public void sendMessage(@RequestBody Message message) {
        message.setTimestamp(LocalDateTime.now().toString());
        System.out.println(message);
        //key값 설정해서 kafka에 전송
        try {
            kafkaTemplate.send(KafkaConstants.KAFKA_TOPIC, message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //구독중인 유저에게 돌려주는 곳
    @MessageMapping("kafka/sendMessage/{chatRoomId}")
    @SendTo("kafka/topic/{chatRoomId}")
    public Message broadcastGroupMessage(@Payload Message message, @DestinationVariable String chatRoomId) {
        System.out.println("message");
        return message;
    }
}
