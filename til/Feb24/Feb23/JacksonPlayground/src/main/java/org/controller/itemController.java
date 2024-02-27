package org.controller;

import org.ClientModel.Save;
import org.ClientModel.clientData;
import org.ServerModel.fwItem;
import org.ServerModel.serverData;
import org.ServerModel.serverManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Optional;

@RestController
public class itemController {
    private static final Logger logger = LoggerFactory.getLogger(itemController.class);
    @PostMapping("/itemRequest")
    //the @validated annotation can be used to perform data validation as specified in the POJO class
    //validation also invokes 'specific group' save.class based rules in case multiple rules are found for the parameters
    public ResponseEntity<serverData> createItem(@RequestBody @Validated(Save.class) clientData data)
    {
        logger.info("processing create request");
        data.printPOJO();
        serverManager manager = new serverManager();
        manager.setID(90);
        manager.setZone("AZ1");
        serverData outData = new serverData(9);
        outData.setManager(manager);
        Collections.singletonMap("key1","value1").entrySet().forEach(e->{
            String key = e.getKey();
            String val = e.getValue();
            outData.addProperties(key,val);
        });
        outData.setSID(100);
        outData.setMyZone("south");
        outData.setSZone("north");
        outData.getProperties().put("key2","value2");
        outData.setItem(
                fwItem.builder()
                        .itemID(2100)
                        .model("model x")
                        .max_thput(100)
                        .min_thput(12)
                        .build()

        );
        manager.addToList(outData);
        return ResponseEntity.of(Optional.of(outData));

    }
}
