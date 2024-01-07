package com.blog.interceptors;

import org.springframework.lang.Nullable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.blog.models.CustomUserDetails;
import com.blog.models.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class GetUserInterceptor implements HandlerInterceptor {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            @Nullable ModelAndView modelAndView) throws Exception {
        try {

            CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getPrincipal();
            if (modelAndView != null && customUserDetails != null && customUserDetails.getUser() != null) {
                User user = customUserDetails.getUser();
                modelAndView.addObject("userFullName", user.getFullName());
                modelAndView.addObject("userImageUrl", user.getImageUrl());
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

}
