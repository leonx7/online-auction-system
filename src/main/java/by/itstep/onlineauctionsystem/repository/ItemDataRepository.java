package by.itstep.onlineauctionsystem.repository;

import by.itstep.onlineauctionsystem.model.item.ItemData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemDataRepository extends JpaRepository<ItemData, Long> {
}
