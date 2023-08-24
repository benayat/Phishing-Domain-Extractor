package com.example.json_to_db.config;

import com.example.json_to_db.batch.RedisReader;
import com.example.json_to_db.model.PhishDetails;
import com.example.json_to_db.model.PhishtankPhishDetails;
import com.example.json_to_db.model.dto.PhishtankPhishDetailsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.PassThroughLineMapper;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ReadersConfig {
    @Qualifier("phishingDatabaseLinksResource")
    private final FileSystemResource phishingDatabaseLinksFileSystemResource;
    @Qualifier("phistankLinksResource")
    private final FileSystemResource phishtankLinksFileSystemResource;
    private final StringRedisTemplate stringRedisTemplate;

    @Bean("phishtankSimpleJsonReader")
    public JsonItemReader<PhishDetails> phishtankSimpleReader() {
        return new JsonItemReaderBuilder<PhishDetails>()
                .jsonObjectReader(new JacksonJsonObjectReader<>(PhishDetails.class))
                .resource(phishtankLinksFileSystemResource)
                .name("phishDetailsJsonItemReader")
                .build();
    }
    @Bean
    public FlatFileItemReader<String> textItemReader() {
        return new FlatFileItemReaderBuilder<String>()
                .name("textItemReader")
                .resource(phishingDatabaseLinksFileSystemResource)
                .lineMapper(new PassThroughLineMapper())
                .build();
    }
    @Bean
    public RedisReader redisReader() {
        return new RedisReader(stringRedisTemplate);
    }


    @Bean("phishtankInitialJsonReader")
    public JsonItemReader<PhishtankPhishDetails> phishtankReader() {
        return new JsonItemReaderBuilder<PhishtankPhishDetails>()
                .jsonObjectReader(new JacksonJsonObjectReader<>(PhishtankPhishDetails.class))
                .resource(phishtankLinksFileSystemResource)
                .name("phishDetailsJsonItemReader")
                .build();
    }
    @Bean("phishtankFinalJsonReader")
    public JsonItemReader<PhishtankPhishDetailsDto> phishtankFinalReader() {
        return new JsonItemReaderBuilder<PhishtankPhishDetailsDto>()
                .jsonObjectReader(new JacksonJsonObjectReader<>(PhishtankPhishDetailsDto.class))
                .resource(phishtankLinksFileSystemResource)
                .name("phishDetailsJsonItemReader")
                .build();
    }


}
