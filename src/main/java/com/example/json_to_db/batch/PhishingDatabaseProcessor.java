package com.example.json_to_db.batch;

import com.example.json_to_db.model.dto.PhishDetailsDto;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PhishingDatabaseProcessor implements ItemProcessor<String, PhishDetailsDto> {

        @Override
        public PhishDetailsDto process(@NotNull String url) {
            try {
                PhishDetailsDto phishDetailsDto = PhishDetailsDto.builder()
                        .domain(url.split("/")[2])
                        .build();
                phishDetailsDto.addUrl(url);
                return phishDetailsDto;
            } catch (ArrayIndexOutOfBoundsException e) {
                log.error("Error processing url: {}", url);
                return null;
            }
        }
}
