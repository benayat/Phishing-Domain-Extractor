package com.example.json_to_db.config;

import com.example.json_to_db.model.dto.PhishDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@RequiredArgsConstructor
public class WritersConfig {

    private final MongoTemplate mongoTemplate;
    @Bean
    public MongoItemWriter<PhishDetailsDto> mongoItemWriter() {
        MongoItemWriter<PhishDetailsDto> writer = new MongoItemWriter<>();
        writer.setTemplate(mongoTemplate);
        writer.setCollection("phishing_collection");
        return writer;
    }
}
