package com.blog.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.models.Category;
import com.blog.repositories.CategoryRepository;
import com.blog.services.CategoryService;
import com.blog.services.UtilService;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private UtilService utilService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll(Sort sort) {
        return categoryRepository.findAll(sort);
    }

    @Override
    public Category create(Category category) {
        try {
            category.setSlug(utilService.toSlug(category.getName()));
            return categoryRepository.save(category);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Optional<Category> findById(String id) {
        return categoryRepository.findById(id);

    }

    @Override
    public Category update(Category category) {
        try {
            category.setSlug(utilService.toSlug(category.getName()));
            return categoryRepository.save(category);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Boolean deleteById(String id) {
        try {
            categoryRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Page<Category> paginate(Map<String, String> queryParams) {
        Pageable pageable = utilService.getPageable(queryParams, 10);

        String q = queryParams.get("q");
        String keyword = q == null ? "" : q;

        return categoryRepository.findByNameIgnoreCaseContainingOrSlugIgnoreCaseContaining(keyword, keyword, pageable);
    }

    @Override
    public List<Category> findExciting(Integer limit) {
        Map<String, String> categoryMap = new HashMap<>();
        categoryMap.put("sort_by", "name");
        categoryMap.put("sort_type", "asc");
        Pageable pageable = utilService.getPageable(categoryMap, limit);
        return categoryRepository.findAll(pageable).getContent();
    }

    @Override
    public Optional<Category> findBySlug(String slug) {
        return this.categoryRepository.findBySlug(slug);
    }

}
