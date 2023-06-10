package com.example.json_to_db.config;

import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Configuration
public class ResourceConfig {
    @Value("${phishing.sources.phishing_database.links.url}")
    private String phishingDatabaseLinksUrl;
    @Value("${phishing.sources.phishing_database.domains.url}")
    private String phishingDatabaseDomainsUrl;
    @Value("${phishing.sources.phishing_database.text.links.file_path}")
    private String phishingDatabaseLinksTextFilePath;
    @Value("${phishing.sources.phishing_database.text.domains.file_path}")
    private String phishingDatabaseDomainsTextFilePath;
    @Value("${phishing.sources.phishtank.links.url}")
    private String phistankLinksUrl;
    @Value("${phishing.sources.phishtank.json.file_path}")
    private String phishtankLinksJsonFilePath;

    @Bean("phishingDatabaseLinksResource")
    public FileSystemResource getPhishingDatabaseLinksClassPathResource() {
        return getResource(phishingDatabaseLinksUrl, phishingDatabaseLinksTextFilePath);
    }
    @Bean("phishingDatabaseDomainsResource")
    public FileSystemResource getPhishingDatabaseDomainsClassPathResource() {
        return getResource(phishingDatabaseDomainsUrl, phishingDatabaseDomainsTextFilePath);
    }
    @Bean("phistankLinksResource")
    public FileSystemResource getPhishtankLinksClassPathResource() {
        return getResource(phistankLinksUrl, phishtankLinksJsonFilePath);
    }


    private static FileSystemResource getResource(String url, String targetFilePath) {
        try{
            FileSystemResource resource = new FileSystemResource(targetFilePath);
            if (!resourceExists(resource)) {
                downloadAndUnzip(url, targetFilePath);
            }
            return resource;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void downloadAndUnzip(String sourceUrl, String targetFile) throws IOException {
        try (InputStream inputStream = new GzipCompressorInputStream(new URL(sourceUrl).openStream());
             BufferedOutputStream fileOutputStream = new BufferedOutputStream(new FileOutputStream(targetFile))) {
            IOUtils.copy(inputStream, fileOutputStream);
        }
    }


    private static boolean resourceExists(Resource resource) throws IOException {
        return resource.exists() && resource.isReadable();
    }
}
