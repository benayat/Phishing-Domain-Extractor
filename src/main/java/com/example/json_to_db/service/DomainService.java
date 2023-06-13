package com.example.json_to_db.service;

import com.example.json_to_db.model.sql.Domain;
import com.example.json_to_db.model.sql.Url;
import com.example.json_to_db.repository.DomainRepository;
import com.example.json_to_db.repository.UrlRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DomainService {

    private final DomainRepository domainRepository;
    private final UrlRepository urlRepository;


    @Transactional
    public Domain addDomain(Domain domain) {
        return domainRepository.save(domain);
    }

    @Transactional
    public void processUrls(List<String> urlStrings) {
        for (String urlString : urlStrings) {
            String domainName = extractDomainFromUrl(urlString); // implement this method
            addUrlToDomain(domainName, urlString);
        }
    }
    private static String extractDomainFromUrl(String urlString) {
        return urlString.split("/")[2];
    }
}
