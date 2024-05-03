package org.Repository;

import org.Entity.Rate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
    public interface rateRepository extends JpaRepository<Rate, Integer> {
        Optional<Rate> findByType(String type);
    }

