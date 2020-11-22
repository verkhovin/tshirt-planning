package me.tshirtplanning.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AwsStatusResource {
  @GetMapping
  public ResponseEntity<String> manualVerify() {
    return ResponseEntity.ok("Alive");
  }
}
