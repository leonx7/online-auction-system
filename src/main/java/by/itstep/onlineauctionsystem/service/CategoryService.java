package by.itstep.onlineauctionsystem.service;

import by.itstep.onlineauctionsystem.entity.category.Category;
import by.itstep.onlineauctionsystem.dto.CategoryDto;
import by.itstep.onlineauctionsystem.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    public Category createCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        categoryRepository.save(category);
        return category;
    }

    public List<CategoryDto> getCategories() {
        List<CategoryDto> categoriesDto = new ArrayList<>();
        List<Category> categories = categoryRepository.findAll();
        for (Category category : categories) {
            categoriesDto.add(createCategoryDto(category));
        }
        return categoriesDto;
    }

    public Category getCategoryById(Integer id) {
        Optional<Category> categotyOpt = categoryRepository.findById(id);
        Category category = categotyOpt.get();
        return category;
    }

    public CategoryDto createCategoryDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(String.valueOf(category.getId()));
        categoryDto.setName(category.getName());
        categoryDto.setParentId(String.valueOf(category.getParentId()));
        categoryDto.setLowerBound(String.valueOf(category.getLowerBound()));
        categoryDto.setUpperBound(String.valueOf(category.getUpperBound()));
        return categoryDto;
    }
}
