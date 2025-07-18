package com.pl.edu.wut.master.thesis.bug.service;

import com.pl.edu.wut.master.thesis.bug.model.ai.AiEstimationDto;
import org.springframework.stereotype.Service;

@Service
public interface  AiEstimationService {
    AiEstimationDto estimateTime(String issueKey);
}
