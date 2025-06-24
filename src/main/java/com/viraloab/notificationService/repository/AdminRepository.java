package com.viraloab.notificationService.repository;

import com.viraloab.notificationService.pojo.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public interface AdminRepository extends MongoRepository<Admin, String> {
    Optional<Admin> findByOrganisation(String organisation);
}
