package com.pl.edu.wut.master.thesis.bug.service.implementation;

import com.pl.edu.wut.master.thesis.bug.model.ai.AiEstimationDto;
import com.pl.edu.wut.master.thesis.bug.service.AiEstimationService;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class AiEstimationServiceImplementation implements AiEstimationService {

    @Override
    public AiEstimationDto estimateTime(String issueKey) {
        return new AiEstimationDto( issueKey, 0.0, 0.0,
                OffsetDateTime.now(), "No AI backend configured"
        );
    }
}
