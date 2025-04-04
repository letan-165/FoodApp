package com.app.OtherService.Repository;

import com.app.OtherService.Entity.ChatBot;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatBotRepository extends MongoRepository<ChatBot,String> {
}
