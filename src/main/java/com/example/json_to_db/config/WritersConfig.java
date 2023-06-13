package com.example.json_to_db.config;

import com.example.json_to_db.batch.PhishDetailsWriter;
import com.example.json_to_db.model.dto.PhishDetailsDto;
import com.example.json_to_db.model.dto.PhishtankPhishDetailsDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@RequiredArgsConstructor
public class WritersConfig {

    private final MongoTemplate mongoTemplate;
    @Bean
    public PhishDetailsWriter mongoItemWriter() {
        PhishDetailsWriter writer = new PhishDetailsWriter(mongoTemplate);
        writer.setTemplate(mongoTemplate);
        writer.setCollection("phishing_collection");
        return writer;
    }
    @Bean
    public JsonFileItemWriter<PhishtankPhishDetailsDto> writer() {
        ObjectMapper objectMapper = new ObjectMapper(); // from com.fasterxml.jackson.databind.ObjectMapper
        JacksonJsonObjectMarshaller<PhishtankPhishDetailsDto> jsonObjectMarshaller = new JacksonJsonObjectMarshaller<>();
        jsonObjectMarshaller.setObjectMapper(objectMapper);

        return new JsonFileItemWriterBuilder<PhishtankPhishDetailsDto>()
                .jsonObjectMarshaller(jsonObjectMarshaller)
                .resource(new FileSystemResource("src/main/resources/processed_json_data.json"))
                .name("outputDataWriter")
                .build();
    }
}
