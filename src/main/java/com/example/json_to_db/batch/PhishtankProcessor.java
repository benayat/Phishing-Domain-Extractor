package com.example.json_to_db.batch;

import com.example.json_to_db.model.PhishtankPhishDetails;
import com.example.json_to_db.model.dto.PhishtankPhishDetailsDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class PhishtankProcessor implements ItemProcessor<PhishtankPhishDetails, PhishtankPhishDetailsDto> {

        @Override
        public PhishtankPhishDetailsDto process(@NotNull PhishtankPhishDetails phishtankPhishDetails) {
                return PhishtankPhishDetailsDto.builder()
                        .phishId(Integer.parseInt(phishtankPhishDetails.getPhishId()))
                        .url(phishtankPhishDetails.getUrl())
                        .domain(phishtankPhishDetails.getUrl().split("/")[2])
                        .build();
        }
}
