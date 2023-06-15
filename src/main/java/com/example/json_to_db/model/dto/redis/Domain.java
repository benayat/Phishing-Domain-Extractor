package com.example.json_to_db.model.dto.redis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;

@RedisHash("domain")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Domain implements Serializable {
        private String name;
        private List<String> urls;
}
