package org.App;

import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.redisClient.jedisClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

// enable shed lock for task exclusivity in a multi-threaded env
@SpringBootApplication
@EnableScheduling
@EnableSchedulerLock(defaultLockAtMostFor = "2m")
@ComponentScan("org.AppConfig")
@ComponentScan("org.redisClient")
@ComponentScan("org.Dispatcher")
public class startup {
    private static Logger logger = LoggerFactory.getLogger(startup.class);

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(startup.class,args);
        logger.info("application started");
        if(ctx.getEnvironment().acceptsProfiles("redis")) {
            jedisClient client = ctx.getBean(jedisClient.class);
            client.doPublish();
        }
    }
}