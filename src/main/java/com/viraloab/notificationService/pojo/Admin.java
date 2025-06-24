package com.viraloab.notificationService.pojo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
public class Admin {
    @MongoId
    private String organisation;
    private String apiKey;
}
