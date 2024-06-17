package com.mouadhkh.github_crawler.service;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface UpdateService {
    void checkForUpdates() throws IOException;
}
