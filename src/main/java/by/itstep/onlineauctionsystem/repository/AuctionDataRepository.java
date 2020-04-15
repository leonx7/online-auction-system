package by.itstep.onlineauctionsystem.repository;

import by.itstep.onlineauctionsystem.model.item.AuctionData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionDataRepository extends JpaRepository<AuctionData, Long> {
}
