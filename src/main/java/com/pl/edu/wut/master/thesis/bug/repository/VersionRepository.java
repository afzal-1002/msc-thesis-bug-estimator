package com.pl.edu.wut.master.thesis.bug.repository;

import com.pl.edu.wut.master.thesis.bug.model.version.Version;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VersionRepository extends JpaRepository<Version, Long> {
}
