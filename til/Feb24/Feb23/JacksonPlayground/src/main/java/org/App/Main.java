package org.App;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.format.datetime.DateFormatter;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

@SpringBootApplication
@ComponentScan("org.controller")
public class Main {
    private static Logger logger= LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Main.class,args);
        logger.info("application started");
        LocalDateTime ltd = LocalDateTime.now(); // Local date time w/o any notion of TZ
        ZonedDateTime ztd = ZonedDateTime.now(); // Local date time with info on TZ and offset from UTC
        OffsetDateTime otd = OffsetDateTime.now(); // Local date time with offset info from UTC
        Instant itd = Instant.now(); // UTC date time
        logger.info(ltd.toString());
        logger.info(ztd.toString());
        logger.info(otd.toString());
        logger.info(itd.toString());
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // custom formatting of ztd to
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SS").withZone(ZoneOffset.UTC); // need to use this pattern when doing custom format of instant
        logger.info(df.format(ztd));
        logger.info(fmt.format(itd));
        ZonedDateTime ppp = ZonedDateTime.ofInstant(ltd, ZoneOffset.ofHoursMinutes(0,0),ZoneId.of("Africa/Tunis")); // take the instant , apply the offset and show the resulting time in africa/tunis tz as ztd
        //SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        logger.info(ppp.toString());


    }
}