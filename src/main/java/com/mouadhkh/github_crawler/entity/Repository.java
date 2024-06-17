package com.mouadhkh.github_crawler.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "repository")
public class Repository {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String url;

    public Repository(String name, String url) {
        this.name = name;
        this.url = url;
    }

    @ManyToMany
    @JoinTable(
            name = "developer_repository",
            joinColumns = @JoinColumn(name = "repository_id"),
            inverseJoinColumns = @JoinColumn(name = "developer_id"))
    private List<Developer> developers = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "repository_languages", joinColumns = @JoinColumn(name = "repository_id"))
    @Column(name = "languages")
    private List<String> languages = new ArrayList<>();

}

