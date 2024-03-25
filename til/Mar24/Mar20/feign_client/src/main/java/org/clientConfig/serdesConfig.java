package org.clientConfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import feign.*;
import feign.codec.*;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import lombok.extern.slf4j.Slf4j;
import org.clientModel.jacksonClientData;
import org.serverModel.serverData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.commons.httpclient.OkHttpClientFactory;
import org.springframework.cloud.openfeign.FeignCircuitBreaker;
import org.springframework.cloud.openfeign.FeignClientBuilder;
import org.springframework.cloud.openfeign.FeignFormatterRegistrar;
import org.springframework.cloud.openfeign.support.FeignHttpClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.Duration;
import java.util.function.Function;



public class serdesConfig {
    private static final Logger logger = LoggerFactory.getLogger(serdesConfig.class);
    // custom decoder class that re-uses jackson decoder
    static class customDecoder implements Decoder {

        final Decoder json_dec;

        customDecoder(ObjectMapper mapper) {

            json_dec = new JacksonDecoder(mapper);
        }

        @Override
        public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
            if (response.status() == 200) {
                logger.info("resp code is: 200 ");
            }
            if (type == serverData.class) {
                logger.info("type matched");
            }
            serverData data = (serverData) json_dec.decode(response, serverData.class);
            return data;
        }
    }
    // custom encoder class that reuses jackson encoder
    static class customEncoder implements Encoder {
        final JacksonEncoder json_enc;

        customEncoder(ObjectMapper mapper) {

            json_enc = new JacksonEncoder(mapper);
        }

        @Override
        public void encode(Object o, Type type, RequestTemplate requestTemplate) throws EncodeException {

            json_enc.encode(o, jacksonClientData.class, requestTemplate);
        }
    }

    // configuring client decorators
    // i can use this block to setup ssl as well
    @Bean
    public Feign.Builder getFeignBuilder() {
        // resp interceptor is invoked only when implemented in this style
        return Feign.builder().responseInterceptor(getResponseInterceptor());
    }

    // setting up the encoder with java time module added for java.time API's
    @Bean
    public Encoder serdesEncoder() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModules(new JavaTimeModule());
        //return new JacksonEncoder(mapper);
        return new customEncoder(mapper);

    }

    // setting up the decoder with java time module added for java.time API's
    @Bean
    public Decoder serdesDecoder() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModules(new JavaTimeModule());
        return new customDecoder(mapper);
    }
   /* @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> getCustom()
    {
        CircuitBreakerConfig cbConfig = CircuitBreakerConfig.custom()
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                .minimumNumberOfCalls(2)
                .slidingWindowSize(5)
                .failureRateThreshold(20.0f)
                .waitDurationInOpenState(Duration.ofSeconds(5))
                .permittedNumberOfCallsInHalfOpenState(5)
                .build();


        return resilience4JCircuitBreakerFactory -> resilience4JCircuitBreakerFactory.configure(builder ->
                builder.timeLimiterConfig(new TimeLimiterConfig.Builder().timeoutDuration(Duration.ofSeconds(120)).build())
                        .circuitBreakerConfig(cbConfig), "jacksonClient#createTask()");

    }
   */

    // request interceptor with app specific logic
    @Bean
    public RequestInterceptor getRequestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {

                requestTemplate.header("custom-header", "custom-value");
            }
        };
    }

    // response interceptor with app specific logic
    @Bean
    public ResponseInterceptor getResponseInterceptor() {
        return new ResponseInterceptor() {
            @Override
            public Object aroundDecode(InvocationContext invocationContext) throws IOException {
                logger.info("resp inv is invoked");
                return invocationContext.proceed();
            }
        };
    }

    // custom error decoder for all 4xx and 5xx error codes
    @Bean
    public ErrorDecoder getErrorDecoder() {
        return new ErrorDecoder() {
            private final ErrorDecoder decoder = new Default();

            @Override
            public Exception decode(String s, Response response) {
                if (response.status() == 400) {
                    // custom retry logic when the error code is 400
                    return new RetryableException(response.status(),
                            feign.FeignException.errorStatus(s,response).getMessage(),
                            response.request().httpMethod(),
                            feign.FeignException.errorStatus(s,response),
                            null,
                            response.request());
                }
                // all other cases
                return decoder.decode(s, response);
            }
        };
    }


}
