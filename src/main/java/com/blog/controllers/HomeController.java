package com.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.blog.models.Category;
import com.blog.services.CategoryService;

@Controller
public class HomeController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public String index(Model model) {
        Sort categorySort = Sort.by("name");
        categorySort.ascending();
        List<Category> categoryList = categoryService.findAll(categorySort);
        model.addAttribute("categoryList", categoryList);
        return "index";
    }
}
