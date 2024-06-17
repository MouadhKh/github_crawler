package com.mouadhkh.github_crawler.dto;

import lombok.Data;

import java.util.List;

@Data
public class RepositoryDTO {
    private String name;
    private String url;
    private List<String> languages;

    public RepositoryDTO(String name, String url, List<String> languages) {
        this.name = name;
        this.url = url;
        this.languages = languages;
    }
}
