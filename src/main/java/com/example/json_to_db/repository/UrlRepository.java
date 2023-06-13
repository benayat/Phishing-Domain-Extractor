package com.example.json_to_db.repository;

import com.example.json_to_db.model.sql.Url;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<Url, Long> {
}
