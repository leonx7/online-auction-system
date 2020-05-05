package by.itstep.onlineauctionsystem.repository;

import by.itstep.onlineauctionsystem.entity.item.Image;
import by.itstep.onlineauctionsystem.entity.item.ItemData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByItemData(ItemData itemData);
}
