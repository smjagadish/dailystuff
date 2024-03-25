package org.clientImpl;

import feign.Response;
import org.clientInterface.jacksonClient;
import org.clientModel.jacksonClientData;
import org.serverModel.serverData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class jacksonImpl {
    @Autowired
    private jacksonClient jclient;
    public serverData createTask(jacksonClientData jcd)
    {
        return jclient.createTask(jcd);
    }
}
