package com.blog.controllers;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.blog.models.Category;
import com.blog.services.CategoryService;
import com.blog.services.CloudinaryService;

@Controller
@RequestMapping("/admin")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @GetMapping("/category")
    public String categoryPage(Model model, @RequestParam Map<String, String> queryParams) {
        Page<Category> page = this.categoryService.paginate(queryParams);
        String currentPage = queryParams.get("p");
        String keyword = queryParams.get("q");
        model.addAttribute("categoryList", page.getContent());
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("currentPage", currentPage == null ? 1 : Integer.parseInt(currentPage));
        model.addAttribute("keyword", keyword == null ? "" : keyword);
        model.addAttribute("title", "Quản lý danh mục");

        return "admin/category/index";
    }

    @GetMapping("/category/add")
    public String addCategoryPage(Model model) {
        Category category = new Category();
        category.setIsActive(true);
        category.setName("Action");
        category.setDescription("Description Action Category");
        model.addAttribute("category", category);
        model.addAttribute("title", "Thêm mới danh mục");
        return "admin/category/add";
    }

    @PostMapping("/category/add")
    public String addCategory(@ModelAttribute("category") Category category,
            @RequestParam("image") MultipartFile file) {
        try {
            String imageUrl = cloudinaryService.uploadFile(file);
            category.setImageUrl(imageUrl);
        } catch (Exception e) {
        }
        if (this.categoryService.create(category) != null) {
            return "redirect:/admin/category";
        }

        return "admin/category/add";
    }

    @GetMapping("/category/{categoryId}/edit")
    public String editCategoryPage(@PathVariable("categoryId") String categoryId, Model model) {
        Optional<Category> resultCategory = this.categoryService.findById(categoryId);
        Category category = new Category();
        if (resultCategory.isPresent()) {
            category = resultCategory.get();
        }
        model.addAttribute("category", category);
        model.addAttribute("title", "Chỉnh sửa danh mục " + category.getName());
        return "admin/category/edit";
    }

    @PostMapping("/category/edit")
    public String editCategory(
            @ModelAttribute("category") Category category, Model model,
            @RequestParam("image") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                String imageUrl = cloudinaryService.uploadFile(file);
                category.setImageUrl(imageUrl);
            }
        } catch (Exception e) {
        }
        if (this.categoryService.update(category) != null)
            return "redirect:/admin/category";
        return "admin/category/edit";
    }

    @GetMapping("/category/{categoryId}/delete")
    public String deleteCategory(@PathVariable("categoryId") String categoryId) {
        Optional<Category> resultCategory = this.categoryService.findById(categoryId);

        if (resultCategory.isPresent()) {
            this.cloudinaryService.deleteURL(resultCategory.get().getImageUrl());
            this.categoryService.deleteById(categoryId);
        }

        return "redirect:/admin/category";
    }
}
