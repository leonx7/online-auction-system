package by.itstep.onlineauctionsystem.service;

import by.itstep.onlineauctionsystem.model.category.Category;
import by.itstep.onlineauctionsystem.model.category.CategoryDto;
import by.itstep.onlineauctionsystem.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public Category createCategory(CategoryDto categoryDto){
        Category category = new Category();
        category.setName(categoryDto.getName());
        categoryRepository.save(category);
        return category;
    }
}
