package org.App;

import org.Entity.Loan;
import org.Repository.LoanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@SpringBootApplication
@EnableJpaRepositories("org.Repository")
@EntityScan("org.Entity")
@ComponentScan(basePackages = "org.Controller,org.RateDTO,org.Service,org.Config")
public class appStart {
    private static final Logger logger = LoggerFactory.getLogger(appStart.class);
    @Autowired
    private LoanRepository loanRepository;
    public static void main(String[] args) {
        logger.info("app starting");
        ApplicationContext ctx = SpringApplication.run(appStart.class,args);
    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @PostConstruct
    public void setupData() {
        loanRepository.saveAll(Arrays.asList(
                Loan.builder().id(1).type("PERSONAL").amount(200000.0).interest(0.0).build(),
                Loan.builder().id(2).type("HOUSING").amount(6000000.0).interest(0.0).build(),
                Loan.builder().id(3).type("PERSONAL").amount(100000.0).interest(0.0).build()
        ));
    }
}