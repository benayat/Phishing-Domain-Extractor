package com.example.json_to_db.repository;

import com.example.json_to_db.model.sql.Domain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DomainRepository extends JpaRepository<Domain, Long> {
}
