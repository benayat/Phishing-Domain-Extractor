package com.example.json_to_db.model.dto;

import com.example.json_to_db.model.PhishDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Vector;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class PhishDetailsDto implements Serializable {
    @Indexed(unique = true) private String domain;
    private Vector<String> urls;

    public void addUrl(String url) {
        if (urls == null) {
            urls = new Vector<>();
        }
        urls.add(url);
    }
}
