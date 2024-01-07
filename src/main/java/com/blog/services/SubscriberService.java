package com.blog.services;

import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.blog.models.Subscriber;

public interface SubscriberService {
    Page<Subscriber> paginate(Map<String, String> queryParams);

    Optional<Subscriber> findByEmail(String email);

    Boolean deleteById(String id);

    Subscriber create(Subscriber subscriber);

}
