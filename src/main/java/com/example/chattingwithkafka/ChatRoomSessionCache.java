package com.example.chattingwithkafka;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class ChatRoomSessionCache {

    private static ChatRoomSessionCache chatRoomSessionCache;
    private LoadingCache<String, String> sessions;

    private ChatRoomSessionCache() {
        //timer를 사용해보기
        sessions = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .expireAfterAccess(7, TimeUnit.MINUTES)
                .build(
                        new CacheLoader<>() {
                            @Override
                            public String load(String key) throws ExecutionException {
                                return sessions.get(key);
                            }
                        });
    }

    public static ChatRoomSessionCache getInstance() {
        if(chatRoomSessionCache==null) {
            chatRoomSessionCache = new ChatRoomSessionCache();
        }
        return chatRoomSessionCache;
    }

    //만약 유저 하나가 여러 세션에 있다면?
    //List를 써야하나
    //Map써도 괜찮을거같기도
    public void setSession(String name, String sessionId) {
        sessions.put(name, sessionId);
    }

    
    //오류 캐치 안함 -> null인경우 생각해야됨
    public String getSession(String name) {
        return sessions.getIfPresent(name);
    }
}
