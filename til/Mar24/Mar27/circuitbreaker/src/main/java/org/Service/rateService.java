package org.Service;

import org.Entity.Rate;
import org.Repository.rateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class rateService {
    private static final Logger logger = LoggerFactory.getLogger(rateService.class);
    @Autowired
    private rateRepository repository;

    public Rate getRateByType(String type) {
        return repository.findByType(type).orElseThrow(() -> new RuntimeException("Rate Not Found: " + type));
    }
}

