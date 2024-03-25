package org.clientImpl;

import org.clientInterface.schedulerClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class schedulerImpl {
    @Autowired
    public schedulerClient client;
    private static Logger logger = LoggerFactory.getLogger(schedulerImpl.class);
    public void doTask()
    {
        logger.info("method invoked");
        Set<String> res;
        res = client.getSchedules();
        res.forEach(e->logger.info("this is"+e));
    }
}
