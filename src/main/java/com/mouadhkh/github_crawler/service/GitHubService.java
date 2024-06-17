package com.mouadhkh.github_crawler.service;

import com.mouadhkh.github_crawler.dto.DeveloperDTO;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

public interface GitHubService {

    /**
     * Set the organization that the service will interact with
     *
     * @param organization
     */
    void setOrganization(String organization);

    /**
     * Get organization
     */
    String getOrganization();

    List<DeveloperDTO> getAllDevelopers();

    /**
     * Could be improved since it pulls developers that collaborated in a repository using given language
     *
     * @param language
     * @return developers that collaborated in a repository using input language
     */
    List<DeveloperDTO> getDevelopersByProgrammingLanguage(String language);

    /**
     * Update Developers & Repositories data from Github
     * This method is called at the start of the application and then periodically every x minutes
     *
     * @throws IOException
     */
    void updateDataFromGitHub() throws IOException;

    Instant getLatestUpdateTimestampFromGitHub() throws IOException;
}
