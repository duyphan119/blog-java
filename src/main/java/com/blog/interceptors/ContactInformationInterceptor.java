package com.blog.interceptors;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.blog.models.ContactInformation;
import com.blog.services.ContactInformationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class ContactInformationInterceptor implements HandlerInterceptor {

    @Autowired
    private ContactInformationService contactInformationService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            @Nullable ModelAndView modelAndView) throws Exception {

        String requestURI = request.getRequestURI();

        // Kiểm tra đường dẫn của URL
        if (requestURI.startsWith("/article") || requestURI.equals("/") || requestURI.equals("/contact")) {
            List<ContactInformation> contactInformationList = contactInformationService.findAll();
            if (modelAndView != null && contactInformationList.size() > 0) {
                modelAndView.addObject("contactInformation", contactInformationList.get(0));
            }
        }
    }
}
