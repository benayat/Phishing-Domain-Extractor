package com.example.json_to_db.batch;

import com.example.json_to_db.model.PhishDetails;
import com.example.json_to_db.model.dto.redis.Url;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class PhishDetailsToUrlPojoProcessor implements ItemProcessor<PhishDetails, Url> {

    @Override
    public Url process(@NotNull PhishDetails phishDetails) {
        try {
            return Url.builder()
                    .url(phishDetails.getUrl())
                    .domain(phishDetails.getUrl().split("/")[2])
                    .build();
        } catch (ArrayIndexOutOfBoundsException e) {
            log.error("Error processing url: {}", phishDetails.getUrl());
            return null;
        }
    }
}