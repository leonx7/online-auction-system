package by.itstep.onlineauctionsystem.controller;

import by.itstep.onlineauctionsystem.model.category.CategoryDto;
import by.itstep.onlineauctionsystem.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CategoryController {

    final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/category")
    public String createCategory() {
        return "category";
    }

    @PostMapping("/category")
    public String createCategory(CategoryDto categoryDto) {
        categoryService.createCategory(categoryDto);
        return "category";
    }
}
