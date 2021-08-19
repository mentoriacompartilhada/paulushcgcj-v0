package io.github.paulushcgcj.paulushcgcjv0.endpoints;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldEndpoint {

    @GetMapping("/api/hello")
    public ResponseEntity<String> getHelloWorld(){
        return ResponseEntity.ok("Hello World");
    }

}
