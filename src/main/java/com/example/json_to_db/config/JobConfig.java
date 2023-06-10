package com.example.json_to_db.config;


import com.example.json_to_db.batch.JobCompletionNotificationListener;
import com.example.json_to_db.batch.PhishingDatabaseProcessor;
import com.example.json_to_db.batch.PhishtankProcessor;
import com.example.json_to_db.model.PhishtankPhishDetails;
import com.example.json_to_db.model.dto.PhishDetailsDto;
import com.example.json_to_db.model.dto.PhishtankPhishDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class JobConfig {

    private final MongoItemWriter<PhishDetailsDto> writer;
    private final JsonItemReader<PhishtankPhishDetails> phishtankPhishDetailsJsonItemReader;
    private final FlatFileItemReader<String> textItemReader;
    private final PhishtankProcessor processor;
    private final PhishingDatabaseProcessor phishingDatabaseProcessor;

    @Bean
    public Job importUserJob(JobRepository jobRepository,
                             JobCompletionNotificationListener listener, @Qualifier("phishtankStep") Step step1, @Qualifier("phishingDatabaseStep") Step step2) {
        return new JobBuilder("importUserJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .next(step2)
                .end()
                .build();
    }

    @Bean("phishtankStep")
    public Step phishtankStep(JobRepository jobRepository,
                              PlatformTransactionManager transactionManager) {
        return new StepBuilder("phishtankStep", jobRepository)
                .<PhishtankPhishDetails, PhishtankPhishDetailsDto>chunk(1000, transactionManager)
                .reader(phishtankPhishDetailsJsonItemReader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean("phishingDatabaseStep")
    public Step phishingDatabaseStep(JobRepository jobRepository,
                                     PlatformTransactionManager transactionManager) {
        return new StepBuilder("phishingDatabaseStep", jobRepository)
                .<String, PhishDetailsDto>chunk(1000, transactionManager)
                .reader(textItemReader)
                .processor(phishingDatabaseProcessor)
                .writer(writer)
                .build();
    }
}
