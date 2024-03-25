package org.clientInterface;

import feign.Response;
import org.clientConfig.schedulerConfig;
import org.clientConfig.serdesConfig;
import org.clientFallback.jacksonFallback;
import org.clientFallback.schedulerFallback;
import org.clientModel.jacksonClientData;
import org.serverModel.serverData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// declarative feign client towards the jackson-playground server
// feign client configuration is coded in the serdesConfig class file
@FeignClient(name= "jackson-feign-client",url = "http://127.0.0.1:8650",configuration = serdesConfig.class)
public interface jacksonClient {
    // client invokes only 1 post method with json for request and response
    @PostMapping(path = "/itemRequest" , produces = "application/json", consumes = "application/json")
    serverData createTask(jacksonClientData jcd);
}
