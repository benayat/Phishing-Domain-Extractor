package com.example.json_to_db.model.sql;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Domain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "domain_id")
    private List<Url> urls;
}