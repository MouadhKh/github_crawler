package com.mouadhkh.github_crawler.repository;

import com.mouadhkh.github_crawler.entity.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DeveloperRepository extends JpaRepository<Developer, Long> {
    Optional<Developer> findByUsername(String username);

    @Query("SELECT d FROM Developer d JOIN d.repositories r JOIN r.languages l WHERE LOWER(l) = LOWER(:language)")
    List<Developer> findDevelopersByLanguage(@Param("language") String language);
}
