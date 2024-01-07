package com.blog.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import com.blog.models.Category;

public interface CategoryService {
    List<Category> findAll(Sort sort);

    Category create(Category category);

    Optional<Category> findById(String id);

    Category update(Category category);

    Boolean deleteById(String id);

    Page<Category> paginate(Map<String, String> queryParams);

    List<Category> findExciting(Integer limit);

    Optional<Category> findBySlug(String slug);
}
