package com.blog.services.impl;

import java.io.StringWriter;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.models.ApiResponse;
import com.blog.services.UtilService;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UtilServiceImpl implements UtilService {
    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

    @Override
    public String toSlug(String text) {
        String nowhitespace = WHITESPACE.matcher(text).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }

    @Override
    public Pageable getPageable(Map<String, String> queryParams, Integer defaultPagesize) {

        if (queryParams == null) {
            Sort sort = Sort.by("createdAt");
            sort.descending();
            return PageRequest.of(0, defaultPagesize, sort);
        }

        String pageNo = queryParams.get("p");
        String pageSize = queryParams.get("limit");
        String sortBy = queryParams.get("sort_by");
        String sortType = queryParams.get("sort_type");

        if (sortBy == null) {
            sortBy = "createdAt";
        }
        Sort sort = Sort.by(sortBy);
        if (sortType == null || sortType.equals("desc")) {
            sort = sort.descending();
        } else {
            sort = sort.ascending();
        }
        Pageable pageable = PageRequest.of(pageNo == null ? 0 : Integer.parseInt(pageNo) - 1,
                pageSize == null ? defaultPagesize : Integer.parseInt(pageSize), sort);

        return pageable;
    }

    @Override
    public String getApiResponseJson(ApiResponse<Object> data) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            StringWriter stringWriter = new StringWriter();
            JsonGenerator jsonGenerator = new JsonFactory().createGenerator(stringWriter);
            objectMapper.writeValue(jsonGenerator, data);
            jsonGenerator.close();
            return stringWriter.toString();
        } catch (Exception e) {
            return null;
        }
    }

}
