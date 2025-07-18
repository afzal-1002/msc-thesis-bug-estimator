package com.pl.edu.wut.master.thesis.bug.service;

import com.pl.edu.wut.master.thesis.bug.model.site.SiteSummary;
import com.pl.edu.wut.master.thesis.bug.dto.site.SiteResponse;
import com.pl.edu.wut.master.thesis.bug.model.site.Site;

import java.util.List;
import java.util.Optional;

public interface SiteService {

    List<SiteSummary> listSitesForCurrentUser();
    SiteResponse selectSite(String siteId);
    Site findSiteById(Long siteId);

    SiteResponse addSite(String siteName);

    Optional<Site> findByCredentialIdAndSitename(Long credentialId, String sitename);

    void deleteSiteById(Long siteId);
    SiteResponse updateSite(Long siteId, String sitename);
    SiteResponse createSite(Site site);
    List<SiteResponse> deleteAllSites();

}
