package by.itstep.onlineauctionsystem.repository;

import by.itstep.onlineauctionsystem.entity.item.Item;
import by.itstep.onlineauctionsystem.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query("SELECT i FROM Item i WHERE i.buyerEmail = ?1")
    List<Item> findItemByBuyer(String buyerEmail);
}
