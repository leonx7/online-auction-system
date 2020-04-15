package by.itstep.onlineauctionsystem.repository;

import by.itstep.onlineauctionsystem.model.item.Image;
import by.itstep.onlineauctionsystem.model.item.ItemData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByItemData(ItemData itemData);
}
