package by.itstep.onlineauctionsystem.repository;

import by.itstep.onlineauctionsystem.model.bidding.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BidRepository extends JpaRepository<Bid, Long> {
}
