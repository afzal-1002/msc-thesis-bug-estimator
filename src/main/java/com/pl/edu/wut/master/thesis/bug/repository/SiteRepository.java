package com.pl.edu.wut.master.thesis.bug.repository;

import com.pl.edu.wut.master.thesis.bug.model.site.Site;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SiteRepository extends JpaRepository<Site, Long> {

    Optional<Site> findSiteById(Long siteId);

    List<Site> findByCredentialUserId(Long userId);

    Optional<Site> findByCredentialIdAndSitename(Long credentialId, String sitename);

}
