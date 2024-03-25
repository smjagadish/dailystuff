package org.clientFallback;

import feign.Response;
import org.clientInterface.jacksonClient;
import org.clientModel.jacksonClientData;
import org.serverModel.serverData;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class jacksonFallback implements jacksonClient {


    @Override
    public serverData createTask(jacksonClientData jcd) {
        return null;
    }
}
