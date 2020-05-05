package by.itstep.onlineauctionsystem.repository;

import by.itstep.onlineauctionsystem.entity.bidding.Bid;
import by.itstep.onlineauctionsystem.entity.item.AuctionData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BidRepository extends JpaRepository<Bid, Long> {
    List<Bid> findByAuctionData(AuctionData auctionData);
}
