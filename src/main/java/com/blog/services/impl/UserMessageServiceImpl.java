package com.blog.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.models.UserMessage;
import com.blog.repositories.UserMessageRepository;
import com.blog.services.UserMessageService;

@Service
public class UserMessageServiceImpl implements UserMessageService {

    @Autowired
    private UserMessageRepository userMessageRepository;

    @Override
    public UserMessage create(UserMessage userMessage) {
        try {
            return userMessageRepository.save(userMessage);
        } catch (Exception e) {
            return null;
        }
    }

}
