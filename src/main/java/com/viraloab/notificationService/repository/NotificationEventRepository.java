package com.viraloab.notificationService.repository;

import com.viraloab.notificationService.pojo.NotificationEventData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationEventRepository extends MongoRepository<NotificationEventData, String> {
}
