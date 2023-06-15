package com.example.json_to_db.batch;

import com.example.json_to_db.model.dto.redis.Url;
import com.example.json_to_db.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class IntermediateRedisWriter implements ItemWriter<Url> {
    private final RedisService redisService;

    @Override
    public void write(Chunk<? extends Url> chunk) {
        log.info("Writing domains chunk of size: {}", chunk.size());
        redisService.addElementsToSortedSet("links", chunk.getItems()
                .parallelStream().map(Url::getUrl).toList());
        redisService.addElementsToSortedSet("domains", chunk.getItems()
                .parallelStream().map(Url::getDomain).toList());
    }
}
