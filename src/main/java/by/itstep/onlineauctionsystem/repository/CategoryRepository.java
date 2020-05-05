package by.itstep.onlineauctionsystem.repository;

import by.itstep.onlineauctionsystem.entity.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
