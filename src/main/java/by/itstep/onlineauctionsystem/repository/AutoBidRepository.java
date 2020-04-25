package by.itstep.onlineauctionsystem.repository;

import by.itstep.onlineauctionsystem.model.bidding.AutoBid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AutoBidRepository extends JpaRepository<AutoBid, Long> {

    @Query("SELECT a FROM AutoBid a WHERE a.maximumBid = (SELECT MAX(a.maximumBid) FROM AutoBid a) ")
    AutoBid findAutoBidWithMaximumBid();
}
