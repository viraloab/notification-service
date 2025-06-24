package com.viraloab.notificationService.producers;

import com.google.gson.Gson;
import com.viraloab.notificationService.configuration.SqsConfig;
import com.viraloab.notificationService.pojo.NotificationEventData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Service
@Slf4j
public class NotificationEventProducer {
    @Value("${mail.notification.isEnabled}")
    private String isEnabled;
    @Value("${aws.sqs.queue}")
    private String queue;
    @Value("${aws.queue.url}")
    private String url;
    @Value("${aws.sqs.group-id}")
    private String groupId;
    @Autowired
    private SqsAsyncClient sqsAsyncClient;
    @Autowired
    private Gson gson;

    public void produceEvent(NotificationEventData eventData) {
        SendMessageRequest request = SendMessageRequest.builder()
                .messageBody(gson.toJson(eventData)).queueUrl(url)
                .messageGroupId(groupId)
                .messageDeduplicationId(eventData.getEmail())
                .build();
        sqsAsyncClient.sendMessage(request);
        log.info("[NotificationEvent] Event publish for [{}]", eventData.getEmail());

    }
}
