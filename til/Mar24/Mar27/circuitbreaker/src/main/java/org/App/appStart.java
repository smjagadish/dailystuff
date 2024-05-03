package org.App;

import org.Entity.Rate;
import org.Repository.rateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@SpringBootApplication
@EnableJpaRepositories("org.Repository")
@EntityScan("org.Entity")
@ComponentScan(basePackages = "org.Controller,org.Service")
public class appStart {
    private static final Logger logger = LoggerFactory.getLogger(appStart.class);
    @Autowired
    private rateRepository rateRepository;
    public static void main(String[] args)  {
        ApplicationContext cx = SpringApplication.run(appStart.class,args);
        logger.info("application started");
    }
    @PostConstruct
    public void setupData() {
        rateRepository.saveAll(Arrays.asList(
                Rate.builder().id(1).type("PERSONAL").rateValue(10.0).build(),
                Rate.builder().id(2).type("HOUSING").rateValue(8.0).build()
        ));
    }
}
