package com.example.json_to_db.batch;

import com.example.json_to_db.model.dto.mongo.Domain;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
@Slf4j
@RequiredArgsConstructor
public class MongoDomainWriter extends MongoItemWriter<Domain> {
    private final MongoTemplate mongoTemplate;

    @Override
    protected void doWrite(Chunk<? extends Domain> chunk) {
        log.info("Writing chunk of size: {}", chunk.size());
        BulkOperations bulkOps = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, Domain.class, "domains");
        bulkOps.insert(chunk.getItems());
        bulkOps.execute();
    }
}
