package com.pl.edu.wut.master.thesis.bug.service.implementation;

import com.pl.edu.wut.master.thesis.bug.model.site.SiteSummary;
import com.pl.edu.wut.master.thesis.bug.dto.site.SiteResponse;
import com.pl.edu.wut.master.thesis.bug.model.site.Site;
import com.pl.edu.wut.master.thesis.bug.model.user.User;
import com.pl.edu.wut.master.thesis.bug.model.user.UserCredential;
import com.pl.edu.wut.master.thesis.bug.repository.SiteRepository;
import com.pl.edu.wut.master.thesis.bug.service.JiraUserService;
import com.pl.edu.wut.master.thesis.bug.service.SiteService;
import com.pl.edu.wut.master.thesis.bug.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SiteServiceImplementation implements SiteService {

    private final SiteRepository    siteRepository;
    private final UserService       userService;
    private final HttpSession       session;
    private final JiraUserService jiraUserService;

    private static final String ATTR_INSTANCE_ID = "instanceId";
    private static final String ATTR_HOST_URL    = "hostUrl";

    /** 1. List all sites for the logged-in user */
    @Override
    @Transactional(readOnly = true)
    public List<SiteSummary> listSitesForCurrentUser() {
        User current = userService.getCurrentUser(session);
        UserCredential cred = current.getJiraCredential();
        return cred.getSites().stream()
                .map(this::toSiteDTO)
                .collect(Collectors.toList());
    }

    /** 2. Select one site (by its String ID) as active in session */
    @Override
    @Transactional
    public SiteResponse selectSite(String siteId) {
        Long id = Long.valueOf(siteId);
        Site site = findSiteById(id);
        User current = userService.getCurrentUser(session);
        if (!site.getCredential().getUser().equals(current)) {
            throw new IllegalArgumentException("Site not found or not yours: " + siteId);
        }
        session.setAttribute(ATTR_INSTANCE_ID, site.getId());
        session.setAttribute(ATTR_HOST_URL,    site.getBaseUrl());
        return mapToResponse(site);
    }

    /** 3. Lookup Site entity by its ID (throws if missing) */
    @Override
    public Site findSiteById(Long siteId) {
        return siteRepository.findById(siteId)
                .orElseThrow(() -> new IllegalArgumentException("Site not found: " + siteId));
    }

    /** 4. Add a new site by hostname/sitename only */
    @Override
    @Transactional
    public SiteResponse addSite(String sitename) {
        User current = userService.getCurrentUser(session);
        UserCredential cred = current.getJiraCredential();
        Optional<Site> existing = findByCredentialIdAndSitename(cred.getId(), sitename);
        if (existing.isPresent()) {
            throw new IllegalArgumentException("Site already added: " + sitename);
        }
        jiraUserService.getUserDetailsFromJira();
        String baseUrl = "https://" + sitename + ".atlassian.net";
        Site site = Site.builder()
                .sitename(sitename)
                .baseUrl(baseUrl)
                .credential(cred)
                .addedAt(OffsetDateTime.now())
                .build();
        return mapToResponse(siteRepository.save(site));
    }

    /** 5. Find a site by credential ID + sitename */
    @Override
    public Optional<Site> findByCredentialIdAndSitename(Long credentialId, String sitename) {
        return siteRepository.findByCredentialIdAndSitename(credentialId, sitename);
    }

    /** 6. Delete a single site by its ID */
    @Override
    @Transactional
    public void deleteSiteById(Long siteId) {
        Site site = findSiteById(siteId);
        User current = userService.getCurrentUser(session);
        if (!site.getCredential().getUser().equals(current)) {
            throw new IllegalArgumentException("Site not found or not yours: " + siteId);
        }
        siteRepository.delete(site);
    }

    /** 7. Update an existing site’s name or URL */
    @Override
    @Transactional
    public SiteResponse updateSite(Long siteId, String sitename) {
        Site existing = findSiteById(siteId);
        User current = userService.getCurrentUser(session);
        if (!existing.getCredential().getUser().equals(current)) {
            throw new IllegalArgumentException("Not authorized to update site " + siteId);
        }
        existing.setSitename(sitename);
        existing.setBaseUrl("https://" + sitename + ".atlassian.net");
        return mapToResponse(siteRepository.save(existing));
    }

    /** 8. Create a site from a full Site object */
    @Override
    @Transactional
    public SiteResponse createSite(Site site) {
        site.setId(null);
        User current = userService.getCurrentUser(session);
        site.setCredential(current.getJiraCredential());
        site.setAddedAt(OffsetDateTime.now());
        return mapToResponse(siteRepository.save(site));
    }

    /** 9. Delete *all* sites for the current user */
    @Override
    @Transactional
    public List<SiteResponse> deleteAllSites() {
        User current = userService.getCurrentUser(session);
        UserCredential cred = current.getJiraCredential();
        List<SiteResponse> responses = cred.getSites().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        siteRepository.deleteAllInBatch(cred.getSites());
        return responses;
    }

    // ─── mapping helpers ────────────────────────────────────────────────

    private SiteSummary toSiteDTO(Site site) {
        return SiteSummary.builder()
                .id(      site.getId()      )
                .sitename(site.getSitename())
                .baseUrl( site.getBaseUrl() )
                .addedAt( site.getAddedAt() )
                .build();
    }

private SiteResponse mapToResponse(Site site) {
        SiteResponse.SiteResponseBuilder builder = SiteResponse.builder();
        User current = userService.getCurrentUser(session);
        builder.username(site.getCredential().getUser().getUsername());

        builder.siteId(site.getId());
        builder.sitename(site.getSitename());
        builder.baseUrl(site.getBaseUrl());
        builder.loginName(current.getJiraCredential().getUser().getLogiName());
        return builder.build();
    }
}
