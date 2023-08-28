package br.com.amparo.backend.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/amparo")
public class AmparoApiController {

    @GetMapping
    public ResponseEntity helloWorld(@RequestParam(value = "name", defaultValue = "World") String name) {
        return ResponseEntity.ok(Map.of("message", "Hello, " + name));
    }
}
