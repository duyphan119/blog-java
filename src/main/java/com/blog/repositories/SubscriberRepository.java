package com.blog.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.models.Subscriber;

public interface SubscriberRepository extends JpaRepository<Subscriber, String> {
    Optional<Subscriber> findByEmail(String email);
}
