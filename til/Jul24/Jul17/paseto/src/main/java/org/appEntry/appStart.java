package org.appEntry;

import org.pasetoSeed.initializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("org.pasetoSeed")
@ComponentScan("org.controller")
@ComponentScan("org.claimModel")
public class appStart {
    private static final Logger logger = LoggerFactory.getLogger(appStart.class);
    public static void main(String[] args)
    {
        ApplicationContext ctx = SpringApplication.run(appStart.class,args);
        initializer obj = ctx.getBean(initializer.class);
        logger.info("application started");
        obj.display();
        logger.info("display method called");
    }
}