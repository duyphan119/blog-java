package com.blog.services;

import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.blog.models.ApiResponse;

public interface UtilService {
    String toSlug(String text);

    Pageable getPageable(Map<String, String> queryParams, Integer defaultPagesize);

    String getApiResponseJson(ApiResponse<Object> data);
}
