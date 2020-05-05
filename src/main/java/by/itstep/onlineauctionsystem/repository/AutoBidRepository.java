package by.itstep.onlineauctionsystem.repository;

import by.itstep.onlineauctionsystem.entity.bidding.AutoBid;
import by.itstep.onlineauctionsystem.entity.item.AuctionData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AutoBidRepository extends JpaRepository<AutoBid, Long> {

    List<AutoBid> findByAuctionData(AuctionData auctionData);

    @Query("SELECT a FROM AutoBid a WHERE a.maximumBid = (SELECT MAX(a.maximumBid) FROM AutoBid a WHERE a = (SELECT a FROM AutoBid a WHERE a.auctionData = ?1)) ")
    AutoBid findAutoBidWithMaximumBid(AuctionData auctionData);
}
