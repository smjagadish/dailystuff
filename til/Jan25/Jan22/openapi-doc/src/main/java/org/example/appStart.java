package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.logging.Logger;

@SpringBootApplication
@ComponentScan("org.controller")
public class appStart {
    static final Logger logger = Logger.getLogger(appStart.class.getName());
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(appStart.class,args);

        logger.info("app started");
    }
}