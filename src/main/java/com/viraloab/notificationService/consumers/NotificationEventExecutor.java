package com.viraloab.notificationService.consumers;

import com.google.gson.Gson;
import com.viraloab.notificationService.pojo.NotificationEventData;
import com.viraloab.notificationService.repository.NotificationEventRepository;
import com.viraloab.notificationService.service.RepositoryService;
import com.viraloab.notificationService.service.impl.GmailService;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;

@Slf4j
@Component
public class NotificationEventExecutor {
    @Value("${mail.notification.isEnabled}")
    private String isEnabled;
    @Autowired
    private Gson gson;
    @Autowired
    private GmailService gmailService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private NotificationEventRepository notificationEventRepository;

    @SqsListener(value = "${aws.sqs.queue}")
    void eventListener(String event) {
        if (Boolean.parseBoolean(isEnabled)) {
            Type eventType = new TypeToken<NotificationEventData>() {
            }.getType();
            NotificationEventData eventData = gson.fromJson(event, eventType);
            repositoryService.saveEvent(eventData);
            gmailService.send(eventData);
        } else {
            log.info("[NotificationEvent] Got Request for - {}", event);
        }
    }
}
