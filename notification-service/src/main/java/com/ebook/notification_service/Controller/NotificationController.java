package com.ebook.notification_service.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return new ResponseEntity<>("Notification Service is running", HttpStatus.OK);
    }
}
