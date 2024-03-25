package org.clientFallback;

import org.clientInterface.schedulerClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class schedulerFallback implements schedulerClient {

    private static final Logger logger = LoggerFactory.getLogger(schedulerFallback.class);

    @Override
    public Set<String> getSchedules() {
        logger.info("inside fallback impl");
        return new HashSet<>();
    }
}
