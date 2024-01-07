package com.blog.controllers;

import java.io.StringWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.blog.models.ApiResponse;
import com.blog.models.UserMessage;
import com.blog.services.UserMessageService;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class UserMessageController {

    @Autowired
    private UserMessageService userMessageService;

    @PostMapping(path = "/api/user-message")
    public ResponseEntity<String> createUserMessage(@RequestBody UserMessage userMessage) {
        try {
            if (userMessage != null) {
                System.out.println(userMessage.getEmail());
            }
            ObjectMapper objectMapper = new ObjectMapper();
            StringWriter stringWriter = new StringWriter();
            JsonGenerator jsonGenerator = new JsonFactory().createGenerator(stringWriter);
            UserMessage newUserMessage = this.userMessageService.create(userMessage);

            objectMapper.writeValue(jsonGenerator,
                    new ApiResponse<>("Cảm ơn lời nhắn của bạn, Chúng tôi sẽ phản hồi qua email", newUserMessage));
            jsonGenerator.close();

            String jsonResult = stringWriter.toString();
            return ResponseEntity.ok(jsonResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
