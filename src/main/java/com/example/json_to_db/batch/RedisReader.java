package com.example.json_to_db.batch;

import com.example.json_to_db.model.dto.redis.Domain;
import org.springframework.batch.item.ItemReader;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.*;


public class RedisReader implements ItemReader<Domain> {

    private final StringRedisTemplate redisTemplate;
    private final Iterator<String> domainIterator;

    public RedisReader(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        Set<Object> keys = redisTemplate.opsForHash().keys("domains");
        this.domainIterator = keys.stream()
                .map(Object::toString)
                .iterator();
    }

    @Override
    public Domain read() {
        if (domainIterator.hasNext()) {
            String domain = domainIterator.next();
            String urlList = (String) redisTemplate.opsForHash().get("domains", domain);
            List<String> urls = Arrays.asList(Objects.requireNonNull(urlList).split(","));
            return new Domain(domain, urls);
        } else {
            return null;
        }
    }
}