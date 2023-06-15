package com.example.json_to_db.batch;


import com.example.json_to_db.model.dto.redis.Url;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class StringToURLPojoProcessor implements ItemProcessor<String, Url> {

    @Override
    public Url process(@NotNull String url) {
        try {
            return Url.builder()
                    .url(url)
                    .domain(url.split("/")[2])
                    .build();
        } catch (ArrayIndexOutOfBoundsException e) {
            log.error("Error processing url: {}", url);
            return null;
        }
    }
}
