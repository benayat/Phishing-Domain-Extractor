package com.example.json_to_db.model.dto;

import com.example.json_to_db.model.PhishDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PhishDetailsDto implements Serializable {
    private String url;
    private String domain;
}
