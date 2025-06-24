package com.viraloab.notificationService.service;

import com.viraloab.notificationService.pojo.NotificationEventData;
import org.springframework.stereotype.Service;

@Service
public interface MessageService {

    void send(NotificationEventData data);
    void sendConfirmationMessage(NotificationEventData data);
    void sendErrorMessage();
}
