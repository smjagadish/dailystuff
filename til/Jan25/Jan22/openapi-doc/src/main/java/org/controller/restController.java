package org.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class restController {
    @GetMapping("/resources")
    public ResponseEntity<String> getResource()
    {
        String data ="i'm alive";
        return ResponseEntity.of(Optional.of(data));
    }
}
