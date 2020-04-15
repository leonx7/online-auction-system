package by.itstep.onlineauctionsystem.repository;

import by.itstep.onlineauctionsystem.model.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
