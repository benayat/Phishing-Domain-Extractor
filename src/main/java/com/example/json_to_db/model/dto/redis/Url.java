package com.example.json_to_db.model.dto.redis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("Url")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Url implements Serializable {

    private String domain;
    private String url;

}
