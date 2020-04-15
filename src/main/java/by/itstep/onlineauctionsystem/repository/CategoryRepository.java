package by.itstep.onlineauctionsystem.repository;

import by.itstep.onlineauctionsystem.model.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
