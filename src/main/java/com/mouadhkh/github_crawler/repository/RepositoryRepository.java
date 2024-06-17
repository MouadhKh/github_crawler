package com.mouadhkh.github_crawler.repository;

import com.mouadhkh.github_crawler.entity.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepositoryRepository extends JpaRepository<Repository, Long> {
    Optional<Repository> findByName(String repoName);
}
