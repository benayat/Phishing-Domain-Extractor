package com.example.json_to_db.batch;

import com.example.json_to_db.model.dto.redis.Domain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class RedisToMongoProcessor implements ItemProcessor<Domain, com.example.json_to_db.model.dto.mongo.Domain> {
    @Override
    public com.example.json_to_db.model.dto.mongo.Domain process(Domain item) {
        return com.example.json_to_db.model.dto.mongo.Domain.builder()
                .name(item.getName())
                .urls(item.getUrls())
                .build();
    }
}
