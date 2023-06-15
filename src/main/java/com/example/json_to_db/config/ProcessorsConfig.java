package com.example.json_to_db.config;

import com.example.json_to_db.batch.PhishDetailsToUrlPojoProcessor;
import com.example.json_to_db.batch.RedisToMongoProcessor;
import com.example.json_to_db.batch.StringToURLPojoProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ProcessorsConfig {
    @Bean
    public RedisToMongoProcessor redisToMongoProcessor() {
        return new RedisToMongoProcessor();
    }
    @Bean
    public StringToURLPojoProcessor stringToURLPojoProcessor() {
        return new StringToURLPojoProcessor();
    }
    @Bean
    public PhishDetailsToUrlPojoProcessor phishDetailsToUrlPojoProcessor() {
        return new PhishDetailsToUrlPojoProcessor();
    }
}
