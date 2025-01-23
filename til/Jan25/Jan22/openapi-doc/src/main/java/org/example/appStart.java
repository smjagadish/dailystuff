package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.logging.Logger;

@SpringBootApplication
public class appStart {
    static final Logger logger = Logger.getLogger(appStart.class.getName());
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(appStart.class,args);

        logger.info("app started");
    }
}