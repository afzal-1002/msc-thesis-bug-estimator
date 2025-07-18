package com.pl.edu.wut.master.thesis.bug.contoller;


import com.pl.edu.wut.master.thesis.bug.dto.site.AddSiteRequest;
import com.pl.edu.wut.master.thesis.bug.model.site.SiteSummary;
import com.pl.edu.wut.master.thesis.bug.dto.site.SiteResponse;
import com.pl.edu.wut.master.thesis.bug.dto.user.UpdateSiteRequest;
import com.pl.edu.wut.master.thesis.bug.model.site.Site;
import com.pl.edu.wut.master.thesis.bug.service.SiteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/wut/sites")
@RequiredArgsConstructor
public class SiteController {
    private final SiteService siteService;

    /** 1) List all sites for the current user */
    @GetMapping
    public ResponseEntity<List<SiteSummary>> listSites() {
        List<SiteSummary> sites = siteService.listSitesForCurrentUser();
        return ResponseEntity.ok(sites);
    }

    /** 2) Select one site as active (stores in session) */
    @PostMapping("/select/{siteId}")
    public ResponseEntity<SiteResponse> selectSite(@PathVariable String siteId) {
        SiteResponse resp = siteService.selectSite(siteId);
        return ResponseEntity.ok(resp);
    }

    /** 3) Fetch raw Site by its ID */
    @GetMapping("/{siteId}")
    public ResponseEntity<Site> getSite(@PathVariable Long siteId) {
        Site site = siteService.findSiteById(siteId);
        return ResponseEntity.ok(site);
    }

    /** 4) Add a site by sitename only */
    @PostMapping("/add")
    public ResponseEntity<SiteResponse> addSite(@RequestBody AddSiteRequest req) {
        SiteResponse resp = siteService.addSite(req.getSitename());
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    /** 5) Find a site by credentialId + sitename */
    @GetMapping("/search")
    public ResponseEntity<Site> findByCredAndName(
            @RequestParam Long credentialId,
            @RequestParam String sitename
    ) {
        Optional<Site> site = siteService.findByCredentialIdAndSitename(credentialId, sitename);
        Site site1 = site.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.ok(site1);
    }

    /** 6) Delete one site by ID */
    @DeleteMapping("/{siteId}")
    public ResponseEntity<Void> deleteOne(@PathVariable Long siteId) {
        siteService.deleteSiteById(siteId);
        return ResponseEntity.noContent().build();
    }

    /** 7) Update name/URL of an existing site */
    @PutMapping("/{siteId}")
    public ResponseEntity<SiteResponse> updateSite( @PathVariable Long siteId,
                                                             @RequestBody UpdateSiteRequest request
    ) {
        SiteResponse resp = siteService.updateSite( siteId, request.getSitename());
        return ResponseEntity.ok(resp);
    }

    /** 8) Create a site from a full Site object */
    @PostMapping("/create")
    public ResponseEntity<SiteResponse> createFull(@RequestBody Site site) {
        SiteResponse resp = siteService.createSite(site);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    /** 9) Delete *all* sites for the current user */
    @DeleteMapping
    public ResponseEntity<List<SiteResponse>> deleteAll() {
        List<SiteResponse> deleted = siteService.deleteAllSites();
        return ResponseEntity.ok(deleted);
    }


}