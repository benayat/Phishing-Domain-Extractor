package com.example.json_to_db.model.dto.mongo;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;

@Document(collection = "domains")
@Data
@Builder
public class Domain implements Serializable {
    @Id @Indexed(unique = true)
    private String name;
    @Field("urls")
    private List<String> urls;
}