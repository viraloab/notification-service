package com.viraloab.notificationService.service;

import com.viraloab.notificationService.pojo.NotificationEventData;
import com.viraloab.notificationService.repository.NotificationEventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

@Service
@EnableAsync
@Slf4j
public class RepositoryService {

    @Autowired
    private NotificationEventRepository eventRepository;

    @Async
    public void saveEvent(NotificationEventData eventData) {
        eventRepository.save(eventData);
    }
}
