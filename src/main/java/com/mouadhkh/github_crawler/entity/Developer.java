package com.mouadhkh.github_crawler.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "developer")
public class Developer {


    public Developer(String username) {
        this.username = username;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;


    @ManyToMany(mappedBy = "developers")
    private List<Repository> repositories = new ArrayList<>();

}
