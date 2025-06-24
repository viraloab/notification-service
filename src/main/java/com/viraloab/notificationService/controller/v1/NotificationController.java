package com.viraloab.notificationService.controller.v1;

import com.viraloab.notificationService.pojo.NotificationEventData;
import com.viraloab.notificationService.producers.NotificationEventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/notification")
public class NotificationController {
    @Autowired
    private NotificationEventProducer eventProducer;
    @PostMapping
    public ResponseEntity sendMail(@RequestBody NotificationEventData eventData) {
        eventProducer.produceEvent(eventData);
        return ResponseEntity.ok().build();
    }

}
