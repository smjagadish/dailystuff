package org.clientConfig;

import feign.Feign;
import feign.Logger;
import feign.Response;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import feign.jackson.JacksonDecoder;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreaker;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.openfeign.FeignCircuitBreaker;
import org.springframework.cloud.openfeign.FeignClientBuilder;
import org.springframework.cloud.openfeign.FeignClientProperties;
import org.springframework.context.annotation.Bean;

import java.time.Duration;

public class schedulerConfig {
   // @Autowired
    //private Resilience4JCircuitBreakerFactory resilience4JCircuitBreakerFactory;
    @Bean
    public Decoder feignJacksonDecoder()
    {
        return new JacksonDecoder();
    }
    @Bean
    public Logger.Level feignLoggerLevel()
    {
        return Logger.Level.FULL;
    }

    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> getCustom()
    {
        CircuitBreakerConfig cbConfig = CircuitBreakerConfig.custom()
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                .slidingWindowSize(5)
                .failureRateThreshold(20.0f)
                .waitDurationInOpenState(Duration.ofSeconds(5))
                .permittedNumberOfCallsInHalfOpenState(5)
                .build();


        return resilience4JCircuitBreakerFactory -> resilience4JCircuitBreakerFactory.configure(builder ->
                builder.timeLimiterConfig(new TimeLimiterConfig.Builder().timeoutDuration(Duration.ofSeconds(120)).build())
                .circuitBreakerConfig(cbConfig), "schedulerClient#getSchedules()");

    }
    @Bean
    public ErrorDecoder customErrorDecoder()
    {
        return new ErrorDecoder() {
            @Override
            public Exception decode(String s, Response response) {
                return new Default().decode(s,response);
            }
        };
    }

}
