package org.App;

import feign.Response;
import org.clientImpl.jacksonImpl;
import org.clientImpl.schedulerImpl;
import org.clientModel.jacksonClientData;
import org.clientModel.siteData;
import org.clientModel.siteType;
import org.clientModel.siteZone;
import org.serverModel.serverData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

import java.nio.charset.Charset;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
@ComponentScan("org.clientImpl")
@ComponentScan("org.clientFallback")
//activate feign clients
@EnableFeignClients("org.clientInterface")

public class appStart {
    private static Logger logger = LoggerFactory.getLogger(appStart.class);

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext ctx = SpringApplication.run(appStart.class, args);
        logger.info("application started");
        ExecutorService es = Executors.newFixedThreadPool(20);
        for (int i = 0; i < 10; i++) {

            es.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        // request data constructor

                                jacksonClientData jcd = jacksonClientData.builder()
                                        .id(134)
                                        .item("ford-tzs")
                                        .site(siteData.builder()
                                                .sid(70)
                                                .geo("west")
                                                .capacity(40)
                                                .type(siteType.Type.GEN5).build())
                                        .baseData(siteZone.builder()
                                                .zone("north")
                                                .size_exp_allowed(false)
                                                .license_key("fljvldjfkl")
                                                .max_cells(180)
                                                .epc_zone("in-house").build())
                                        .quote_time(Instant.now())
                                        .absolute_time("2024-03-05 10:00:49")
                                        .build();
                                // invokes the dynamically created feign client impl
                                serverData sds = ctx.getBean(jacksonImpl.class).createTask(jcd);
                                logger.info("res fired:"+sds.toString());


                        }


                    catch (Exception e) {
                        logger.info(" exception is:" + e);
                    }
                }
            });
        }

    }
}