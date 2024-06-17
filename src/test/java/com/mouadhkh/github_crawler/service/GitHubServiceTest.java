package com.mouadhkh.github_crawler.service;

import com.mouadhkh.github_crawler.dto.DeveloperDTO;
import com.mouadhkh.github_crawler.entity.Developer;
import com.mouadhkh.github_crawler.entity.Repository;
import com.mouadhkh.github_crawler.repository.DeveloperRepository;
import com.mouadhkh.github_crawler.repository.RepositoryRepository;
import jakarta.inject.Inject;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;

import java.util.Collections;
import java.util.List;

import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GitHubServiceTest {

    @InjectMocks
    private GitHubServiceImpl gitHubService;

    @Mock
    private DeveloperRepository developerRepository;

    @Mock
    private RepositoryRepository repositoryRepository;

    @Mock
    private static Environment environment;

    @BeforeAll
    public static void init(){
        environment = mock(Environment.class);

    }
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        gitHubService=new GitHubServiceImpl(developerRepository,repositoryRepository, environment);
    }

    @Test
    public void testSetOrganization() {
        String organization = "testOrg";
        gitHubService.setOrganization(organization);
        assertEquals(organization, gitHubService.getOrganization());
    }

    @Test
    public void testGetAllDevelopers() {
        Developer developer = new Developer("testDev");
        when(developerRepository.findAll()).thenReturn(Collections.singletonList(developer));

        List<DeveloperDTO> developers = gitHubService.getAllDevelopers();

        assertEquals(1, developers.size());
        assertEquals("testDev", developers.get(0).getUsername());
    }
}