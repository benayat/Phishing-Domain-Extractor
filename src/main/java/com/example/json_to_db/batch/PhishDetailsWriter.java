package com.example.json_to_db.batch;

import com.example.json_to_db.model.dto.PhishDetailsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

@RequiredArgsConstructor
@Slf4j
public class PhishDetailsWriter extends MongoItemWriter<PhishDetailsDto> {
    private final MongoTemplate mongoTemplate;

    @Override
    protected void doWrite(Chunk<? extends PhishDetailsDto> chunk) {
        log.info("Writing chunk of size: {}", chunk.size());
        BulkOperations bulkOps = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, "yourCollectionName");
        for (PhishDetailsDto item : chunk) {
            Query query = new Query(Criteria.where("domain").is(item.getDomain())); // assuming T has a getDomain method
            Update update = new Update().addToSet("urls").each(item.getUrls()); // assuming T has a getUrls method
            bulkOps.upsert(query, update);
        }
        bulkOps.execute();
    }
}
