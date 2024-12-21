package org.Config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.function.Consumer;

@Configuration
public class customCircuit {
    CircuitBreakerConfig config = CircuitBreakerConfig.custom()
            .failureRateThreshold(30)
            .minimumNumberOfCalls(2)
            .waitDurationInOpenState(Duration.ofMillis(1000))
            .slidingWindowSize(20)
            .automaticTransitionFromOpenToHalfOpenEnabled(true)
            .permittedNumberOfCallsInHalfOpenState(2)
            .build();
    TimeLimiterConfig tconfig = TimeLimiterConfig.custom()
            .timeoutDuration(Duration.ofSeconds(20))
            .build();
    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> specificConfig()
    {
     /*
       return new Customizer<Resilience4JCircuitBreakerFactory>() {
           @Override
           public void customize(Resilience4JCircuitBreakerFactory resilience4JCircuitBreakerFactory) {
                    resilience4JCircuitBreakerFactory.configure(
                            new Consumer<Resilience4JConfigBuilder>() {
                                @Override
                                public void accept(Resilience4JConfigBuilder resilience4JConfigBuilder) {
                                    resilience4JConfigBuilder.circuitBreakerConfig(config)
                                            .timeLimiterConfig(tconfig)
                                            .build();
                                }
                            }
                    ,"mybreaker");
           }
       } ;

      */

        return resilience4JCircuitBreakerFactory -> resilience4JCircuitBreakerFactory.configure(builder -> builder.circuitBreakerConfig(config)
                .timeLimiterConfig(tconfig)
                .build(),"mybreaker");

    }
}
