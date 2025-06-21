package com.pl.edu.wut.master.thesis.bug.repository;

import com.pl.edu.wut.master.thesis.bug.model.component.ComponentInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComponentRepository extends JpaRepository<ComponentInfo, Long> {
}
