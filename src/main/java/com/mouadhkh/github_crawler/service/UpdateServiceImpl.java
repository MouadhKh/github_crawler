package com.mouadhkh.github_crawler.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;

@Slf4j
@Service
public class UpdateServiceImpl implements UpdateService {
    private static final long ONE_HOUR_IN_MILLISECONDS = 3600000;
    private final GitHubService gitHubService;
    private Instant lastUpdateTimestamp = Instant.EPOCH;

    public UpdateServiceImpl(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @Async
    @Scheduled(fixedRate = ONE_HOUR_IN_MILLISECONDS)
    @Override
    public void checkForUpdates() throws IOException {
        Instant latestUpdateTimestamp = gitHubService.getLatestUpdateTimestampFromGitHub();
        if (latestUpdateTimestamp.isAfter(lastUpdateTimestamp)) {
            log.info("---- Starting Update ----");
            gitHubService.updateDataFromGitHub();
            lastUpdateTimestamp = latestUpdateTimestamp;
            log.info("---- Update Finished! ----");
        }
    }
}
