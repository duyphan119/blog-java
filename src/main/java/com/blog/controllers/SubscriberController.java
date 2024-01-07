package com.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.blog.models.ApiResponse;
import com.blog.models.Subscriber;
import com.blog.services.SubscriberService;
import com.blog.services.UtilService;

@Controller
public class SubscriberController {

    @Autowired
    private UtilService utilService;

    @Autowired
    private SubscriberService subscriberService;

    @PostMapping("/api/subscriber")
    public ResponseEntity<String> createSubscriber(@RequestBody Subscriber subscriber) {
        Subscriber newSubscriber = this.subscriberService.create(subscriber);
        if (newSubscriber == null) {
            return ResponseEntity.status(500).body(utilService
                    .getApiResponseJson(new ApiResponse<>("Error",
                            null)));
        }
        return ResponseEntity.ok(utilService
                .getApiResponseJson(new ApiResponse<>("Success",
                        newSubscriber)));
    }
}
