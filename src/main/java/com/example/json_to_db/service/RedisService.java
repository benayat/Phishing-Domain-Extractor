package com.example.json_to_db.service;

import com.example.json_to_db.model.dto.redis.Url;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RedisService {

    private final ZSetOperations<String, String> zSetOperations;
    private final HashOperations<String, Object, Object> hashOperations;
    private final RedisTemplate<String, String> redisTemplate;

    public RedisService(RedisTemplate<String, String> redisTemplate) {
        this.zSetOperations = redisTemplate.opsForZSet();
        this.hashOperations = redisTemplate.opsForHash();
        this.redisTemplate = redisTemplate;
    }




    public void addElementsToHash(List<Url> urlsChunkList) {
        Map<String,List<String>> domainUrlMapping = getDomainMapFromUrls(urlsChunkList);
        for (Map.Entry<String, List<String>> entry : domainUrlMapping.entrySet()) {
            if (!hashOperations.hasKey("domains", entry.getKey())) {
                String urlsAsString = entry.getValue().parallelStream().distinct().collect(Collectors.joining(","));
                hashOperations.put("domains", entry.getKey(), urlsAsString);
            } else {
                String existingUrlsAsString = (String) hashOperations.get("domains", entry.getKey());
                Set<String> existingUrls = new HashSet<>(Arrays.asList(Objects.requireNonNull(existingUrlsAsString).split(",")));
                existingUrls.addAll(entry.getValue());
                String newUrlsAsString = String.join(",", existingUrls);
                hashOperations.put("domains", entry.getKey(), newUrlsAsString);
            }
        }
    }


    public void addElementsToSortedSet(String key, List<String> elements) {
        Set<ZSetOperations.TypedTuple<String>> tuples = new HashSet<>();
        for (String element : elements) {
            tuples.add(new DefaultTypedTuple<>(element, 0.0));
        }
        zSetOperations.add(key, tuples);
    }

    private Map<String, List<String>> getDomainMapFromUrls(List<Url> urls) {
        Map<String, List<String>> domains = new HashMap<>();
        for (Url url : urls) {
            String domainName = url.getDomain();
            if (!domains.containsKey(domainName)) {
                domains.put(domainName, new ArrayList<>(List.of(url.getUrl())));
            } else {
                domains.get(domainName).add(url.getUrl());
            }
        }
        return domains;
    }

    public void addElementsToHashPipelined(List<Url> urlsChunkList) {
        Map<String,List<String>> domainUrlMapping = getDomainMapFromUrls(urlsChunkList);
        redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            for (Map.Entry<String, List<String>> entry : domainUrlMapping.entrySet()) {
                if (!hashOperations.hasKey("domains", entry.getKey())) {
                    String urlsAsString = entry.getValue().parallelStream().distinct().collect(Collectors.joining(","));
                    connection.hSet("domains".getBytes(), entry.getKey().getBytes(), urlsAsString.getBytes());
                } else {
                    String existingUrlsAsString = (String) hashOperations.get("domains", entry.getKey());
                    Set<String> existingUrls = new HashSet<>(Arrays.asList(Objects.requireNonNull(existingUrlsAsString).split(",")));
                    existingUrls.addAll(entry.getValue());
                    String newUrlsAsString = String.join(",", existingUrls);
                    connection.hSet("domains".getBytes(), entry.getKey().getBytes(), newUrlsAsString.getBytes());
                }
            }
            return null;
        });
    }


}
