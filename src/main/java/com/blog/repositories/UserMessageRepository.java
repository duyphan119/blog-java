package com.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.models.UserMessage;

public interface UserMessageRepository extends JpaRepository<UserMessage, String> {

}
