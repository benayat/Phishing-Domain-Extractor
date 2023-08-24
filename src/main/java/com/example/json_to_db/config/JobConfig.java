package com.example.json_to_db.config;


import com.example.json_to_db.batch.*;
import com.example.json_to_db.model.PhishDetails;
import com.example.json_to_db.model.dto.redis.Domain;
import com.example.json_to_db.model.dto.redis.Url;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class JobConfig {

    private final JsonItemReader<PhishDetails> phishDetailsJsonItemReader;
    private final FlatFileItemReader<String> textItemReader;
    private final RedisReader redisReader;
    private final RedisToMongoProcessor redisToMongoProcessor;
    private final StringToURLPojoProcessor stringToURLPojoProcessor;
    private final PhishDetailsToUrlPojoProcessor phishDetailsToUrlPojoProcessor;
    private final RedisFinalWriter redisFinalWriter;
    private final MongoDomainWriter mongoDomainWriter;



    @Bean
    public Job importUserJob(JobRepository jobRepository,
                             JobCompletionNotificationListener listener,
                             @Qualifier("jsonToRedisStep") Step step1,
                             @Qualifier("textToRedisPojoStep") Step step2,
                             @Qualifier("redisToMongoStep") Step step3){
        return new JobBuilder("importUserJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step3)
                .next(step2)
                .next(step3)
                .end()
                .build();
    }
    @Bean("textToRedisPojoStep")
    public Step textToRedisPojoStep(JobRepository jobRepository,
                                    PlatformTransactionManager transactionManager) {
        return new StepBuilder("textToRedisStep", jobRepository)
                .<String, Url>chunk(50000, transactionManager)
                .reader(textItemReader)
                .processor(stringToURLPojoProcessor)
                .writer(redisFinalWriter)
                .build();
    }
    @Bean("jsonToRedisStep")
    public Step jsonToRedisStep(JobRepository jobRepository,
                                PlatformTransactionManager transactionManager) {
        return new StepBuilder("jsonToDomainStep", jobRepository)
                .<PhishDetails, Url>chunk(50000, transactionManager)
                .reader(phishDetailsJsonItemReader)
                .processor(phishDetailsToUrlPojoProcessor)
                .writer(redisFinalWriter)
                .build();
    }
    @Bean("redisToMongoStep")
    public Step redisToMongoStep(JobRepository jobRepository,
                                 PlatformTransactionManager transactionManager) {
        return new StepBuilder("redisToMongoStep", jobRepository)
                .<Domain, com.example.json_to_db.model.dto.mongo.Domain>chunk(50000, transactionManager)
                .reader(redisReader)
                .processor(redisToMongoProcessor)
                .writer(mongoDomainWriter)
                .build();
    }
}
