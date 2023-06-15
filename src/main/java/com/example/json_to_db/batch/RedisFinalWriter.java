package com.example.json_to_db.batch;

import com.example.json_to_db.model.dto.redis.Url;
import com.example.json_to_db.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class RedisFinalWriter implements ItemWriter<Url> {
    private final RedisService redisService;

    @Override
    public void write(Chunk<? extends Url> chunk) {
        log.info("Writing domains chunk of size: {}", chunk.size());
        redisService.addElementsToHashPipelined((List<Url>) chunk.getItems());
    }
}