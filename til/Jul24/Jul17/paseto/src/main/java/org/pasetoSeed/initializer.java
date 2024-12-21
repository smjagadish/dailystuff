package org.pasetoSeed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class initializer {
    private static final Logger logger = LoggerFactory.getLogger(initializer.class);
    public void display()
    {
       logger.info("draft impl works");
    }
    @Bean
    public secretSeed createSecretBean()
    {
        secretSeed seed = new secretSeed();
      return seed;
    }
}
