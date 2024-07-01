package com.gamelist.seeding.repository;

import com.gamelist.seeding.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserMongoRepository extends MongoRepository<User, String> {
    User findByUsername(String username);
}