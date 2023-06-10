package com.example.json_to_db.config;

import com.example.json_to_db.model.PhishtankPhishDetails;
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
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ReadersConfig {
    @Qualifier("phishingDatabaseLinksClassPathResource")
    private final FileSystemResource phishingDatabaseLinksClassPathResource;
    @Qualifier("phistankLinksClassPathResource")
    private final ClassPathResource phishtankLinksClassPathResource;


    @Bean
    public JsonItemReader<PhishtankPhishDetails> phishtankReader() {
        return new JsonItemReaderBuilder<PhishtankPhishDetails>()
                .jsonObjectReader(new JacksonJsonObjectReader<>(PhishtankPhishDetails.class))
                .resource(phishtankLinksClassPathResource)
                .name("phishDetailsJsonItemReader")
                .build();
    }

    @Bean
    public FlatFileItemReader<String> textItemReader() {
        return new FlatFileItemReaderBuilder<String>()
                .name("textItemReader")
                .resource(phishingDatabaseLinksClassPathResource)
                .lineMapper(new PassThroughLineMapper())
                .build();
    }
}
