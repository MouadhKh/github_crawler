package com.mouadhkh.github_crawler.service;

import com.mouadhkh.github_crawler.dto.DeveloperDTO;
import com.mouadhkh.github_crawler.dto.RepositoryDTO;
import com.mouadhkh.github_crawler.entity.Developer;
import com.mouadhkh.github_crawler.entity.Repository;
import com.mouadhkh.github_crawler.repository.DeveloperRepository;
import com.mouadhkh.github_crawler.repository.RepositoryRepository;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.kohsuke.github.*;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Getter
@Setter
@Service
public class GitHubServiceImpl implements GitHubService {
    public static final String GITHUB_TOKEN = "GITHUB_TOKEN";
    private String githubToken;
    private String organization;
    private DeveloperRepository developerRepository;
    private RepositoryRepository repositoryRepository;
    private Environment env;
    // This can be persisted somewhere to handle cases where server shuts down
    private Instant lastUpdateTimestamp = Instant.MIN;


    public GitHubServiceImpl(DeveloperRepository developerRepository, RepositoryRepository repositoryRepository, Environment env) {
        this.developerRepository = developerRepository;
        this.repositoryRepository = repositoryRepository;
        this.env = env;
        this.organization = StringUtils.EMPTY;
        this.githubToken = env.getProperty(GITHUB_TOKEN);

    }

    @Transactional
    @Override
    public void updateDataFromGitHub() throws IOException {
        GitHub github = new GitHubBuilder().withOAuthToken(githubToken).build();
        GHOrganization ghOrganization = github.getOrganization(organization);

        for (GHUser ghUser : ghOrganization.listMembers()) {
            Optional<Developer> optionalDeveloper = developerRepository.findByUsername(ghUser.getLogin());
            Developer developer = optionalDeveloper.orElseGet(() -> new Developer(ghUser.getLogin()));
            log.info("--- Retrieving Developer {} Repositories ---", ghUser.getLogin());
            List<Repository> repositories = new ArrayList<>();
            for (GHRepository ghRepo : ghUser.listRepositories()) {
                log.info("--- Retrieving Repository:{} ---", ghRepo.getHtmlUrl());
                Optional<Repository> optionalRepo = repositoryRepository.findByName(ghRepo.getName());//problematic in real world app: repo names not unique
                Repository repo = optionalRepo.orElseGet(() -> new Repository(ghRepo.getName(), String.valueOf(ghRepo.getHtmlUrl())));
                List<String> languages = new ArrayList<>(ghRepo.listLanguages().keySet());
                repo.setLanguages(languages);
                // Only add the relationship if it does not already exist
                if (!repo.getDevelopers().contains(developer)) {
                    repo.getDevelopers().add(developer);
                    repositoryRepository.save(repo);
                }

                developer.setRepositories(repositories);
                developerRepository.save(developer);
            }
        }
        lastUpdateTimestamp = Instant.now();
    }

    @Transactional
    @Override
    public List<DeveloperDTO> getAllDevelopers() {
        List<Developer> developers = developerRepository.findAll();
        return developers.stream()
                .map(developer -> {
                    List<Repository> repositories = developer.getRepositories();
                    List<RepositoryDTO> repositoryDTOS = repositories.stream().map(repository -> new RepositoryDTO(repository.getName(), repository.getUrl(), repository.getLanguages())).collect(Collectors.toList());
                    List<String> languages = repositoryDTOS
                            .stream()
                            .flatMap(repo -> repo.getLanguages().stream())
                            .distinct()
                            .collect(Collectors.toList());
                    return new DeveloperDTO(developer.getUsername(), languages, repositoryDTOS);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<DeveloperDTO> getDevelopersByProgrammingLanguage(String language) {
        List<Developer> developers = developerRepository.findDevelopersByLanguage(language);
        return developers.stream()
                .map(developer -> {
                    List<RepositoryDTO> repositoryDTOS = developer.getRepositories()
                            .stream()
                            .map(repository -> new RepositoryDTO(repository.getName(),
                                    repository.getUrl(), repository.getLanguages()))
                            .collect(Collectors.toList());
                    List<String> languages = repositoryDTOS.stream()
                            .flatMap(repositoryDTO -> repositoryDTO.getLanguages().stream())
                            .distinct().collect(Collectors.toList());
                    return new DeveloperDTO(developer.getUsername(), languages, repositoryDTOS);
                }).collect(Collectors.toList());
    }

    public Instant getLatestUpdateTimestampFromGitHub() throws IOException {
        GitHub github = new GitHubBuilder().withOAuthToken(githubToken).build();
        GHOrganization organization = github.getOrganization(this.organization);
        Instant latestUpdateTimestamp = Instant.EPOCH;
        for (GHRepository repo : organization.listRepositories()) {
            //this can be refined to check for the type of update & update only newly updated repositories
            Instant repoUpdateTimestamp = repo.getUpdatedAt().toInstant();
            if (repoUpdateTimestamp.isAfter(latestUpdateTimestamp)) {
                return repoUpdateTimestamp;
            }
        }
        return latestUpdateTimestamp;
    }
}
