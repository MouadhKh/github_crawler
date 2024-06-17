package com.mouadhkh.github_crawler;

import com.mouadhkh.github_crawler.exception.CrawlerMainAppException;
import com.mouadhkh.github_crawler.service.GitHubService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@EnableAsync
@EnableScheduling
@SpringBootApplication
public class GithubCrawlerApplication implements ApplicationRunner {

    private final GitHubService gitHubService;


    public GithubCrawlerApplication(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    public static void main(String[] args) {
        SpringApplication.run(GithubCrawlerApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        if (args.containsOption("organization")) {
            String organization = args.getOptionValues("organization").get(0);
            gitHubService.setOrganization(organization);
        } else {
            throw new CrawlerMainAppException("No organization provided. Please provide the GitHub organization as an argument.");
        }
    }
}
