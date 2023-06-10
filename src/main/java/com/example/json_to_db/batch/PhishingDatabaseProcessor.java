package com.example.json_to_db.batch;

import com.example.json_to_db.model.dto.PhishDetailsDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class PhishingDatabaseProcessor implements ItemProcessor<String, PhishDetailsDto> {

        @Override
        public PhishDetailsDto process(@NotNull String url) {
            return PhishDetailsDto.builder()
                    .url(url)
                    .domain(url.split("/")[2])
                    .build();
        }
}
