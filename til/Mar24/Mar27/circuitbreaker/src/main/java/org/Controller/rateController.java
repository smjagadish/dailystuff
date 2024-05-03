package org.Controller;

import org.Service.rateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.Entity.Rate;
@RestController
@RequestMapping("api")
public class rateController {
    private static final Logger logger = LoggerFactory.getLogger(rateController.class);
    @Autowired
    rateService rateService;
    @GetMapping(path = "/rates/{type}")
    public ResponseEntity<Rate> getRateByType(@PathVariable("type") String type) {
        logger.info("rate controller processing the request");
        return ResponseEntity.ok().body(rateService.getRateByType(type));
    }

}
