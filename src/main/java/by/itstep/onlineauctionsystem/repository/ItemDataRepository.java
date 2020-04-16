package by.itstep.onlineauctionsystem.repository;

import by.itstep.onlineauctionsystem.model.category.Category;
import by.itstep.onlineauctionsystem.model.item.ItemData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemDataRepository extends JpaRepository<ItemData, Long> {
    List<ItemData> findAllByCategoryId(Category category);
}
