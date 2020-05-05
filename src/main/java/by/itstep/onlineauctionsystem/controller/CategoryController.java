package by.itstep.onlineauctionsystem.controller;

import by.itstep.onlineauctionsystem.dto.CategoryDto;
import by.itstep.onlineauctionsystem.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String createCategory() {
        return "category";
    }

    @PostMapping
    public String createCategory(CategoryDto categoryDto) {
        categoryService.createCategory(categoryDto);
        return "category";
    }
}
