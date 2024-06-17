package com.mouadhkh.github_crawler.controller;

import com.mouadhkh.github_crawler.dto.DeveloperDTO;
import com.mouadhkh.github_crawler.service.GitHubService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Developer Controller", description = "Operations related to developers")
@RestController
@RequestMapping("/api")
public class DeveloperController {
    private GitHubService gitHubService;

    public DeveloperController(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    /**
     * Retrieve all developers from the crawler database
     *
     * @return All stored developers
     */
    @Operation(summary = "Get all developers", description = "Retrieve a list of all developers.")
    @GetMapping("/developers")
    public List<DeveloperDTO> getAllDevelopers() {
        return gitHubService.getAllDevelopers();
    }

    /**
     * Retrieve developers by programming language
     *
     * @param language
     * @return developers that can develop in given programming language
     */
    @Operation(summary = "Get developers by programming language", description = "Retrieve a list of developers by programming language.")
    @GetMapping("/developers/{language}")
    public List<DeveloperDTO> getDevelopersByProgrammingLanguage(@PathVariable String language) {
        return gitHubService.getDevelopersByProgrammingLanguage(language);
    }
}
