package com.blog.services.impl;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.blog.models.Subscriber;
import com.blog.repositories.SubscriberRepository;
import com.blog.services.SubscriberService;
import com.blog.services.UtilService;

@Service
public class SubscriberServiceImpl implements SubscriberService {
    @Autowired
    private SubscriberRepository subscriberRepository;

    @Autowired
    private UtilService utilService;

    @Override
    public Page<Subscriber> paginate(Map<String, String> queryParams) {
        Pageable pageable = utilService.getPageable(queryParams, 10);

        return subscriberRepository.findAll(pageable);
    }

    @Override
    public Optional<Subscriber> findByEmail(String email) {
        return this.subscriberRepository.findByEmail(email);
    }

    @Override
    public Boolean deleteById(String id) {
        try {
            this.subscriberRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Subscriber create(Subscriber subscriber) {
        try {
            return this.subscriberRepository.save(subscriber);
        } catch (Exception e) {
            return null;
        }
    }
}
