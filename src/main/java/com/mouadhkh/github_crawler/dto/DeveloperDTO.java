package com.mouadhkh.github_crawler.dto;

import lombok.Data;

import java.util.List;

@Data
public class DeveloperDTO {
    private String username;
    private List<String> languages;
    private List<RepositoryDTO> repositories;

    public DeveloperDTO(String username, List<String> languages, List<RepositoryDTO> repositories) {
        this.username = username;
        this.languages = languages;
        this.repositories = repositories;
    }

}