package com.blog.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.models.Category;

public interface CategoryRepository extends JpaRepository<Category, String> {
    Page<Category> findByNameIgnoreCaseContainingOrSlugIgnoreCaseContaining(String name, String slug,
            Pageable pageable);

    Optional<Category> findBySlug(String slug);
}
