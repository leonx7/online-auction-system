package by.itstep.onlineauctionsystem.service;

import by.itstep.onlineauctionsystem.model.bidding.*;
import by.itstep.onlineauctionsystem.model.item.AuctionData;
import by.itstep.onlineauctionsystem.model.item.Item;
import by.itstep.onlineauctionsystem.model.user.User;
import by.itstep.onlineauctionsystem.repository.AuctionDataRepository;
import by.itstep.onlineauctionsystem.repository.AutoBidRepository;
import by.itstep.onlineauctionsystem.repository.BidRepository;
import by.itstep.onlineauctionsystem.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class BiddingService {

    final BidRepository bidRepository;
    final AuctionDataRepository auctionDataRepository;
    final UserRepository userRepository;
    final AutoBidRepository autoBidRepository;

    public BiddingService(BidRepository bidRepository, AuctionDataRepository auctionDataRepository, UserRepository userRepository, AutoBidRepository autoBidRepository) {
        this.bidRepository = bidRepository;
        this.auctionDataRepository = auctionDataRepository;
        this.userRepository = userRepository;
        this.autoBidRepository = autoBidRepository;
    }

    public Bid saveBid(String username, BidRequest bidRequest) {
        User user = userRepository.findByEmail(username);
        Optional<AuctionData> auctionDataOpt = auctionDataRepository.findById(Long.valueOf(bidRequest.getId()));
        AuctionData auctionData = auctionDataOpt.get();
        Bid bid = new Bid();
        bid.setUser(user);
        bid.setAuctionData(auctionData);
        bidRepository.save(bid);
        return bid;
    }

    public BidResponse placeBid(Principal principal, BidRequest bid) throws InterruptedException {
        updatePrice(bid);
        saveBid(principal.getName(), bid);
        Thread.sleep(1000); // simulated delay
        Double nextBid = Double.valueOf(bid.getBid()) + Double.valueOf(bid.getIncrement());
        String username = principal.getName();
        String currentBid = bid.getBid();
        return new BidResponse(HtmlUtils.htmlEscape(String.valueOf(nextBid)), HtmlUtils.htmlEscape(username), HtmlUtils.htmlEscape(currentBid));
    }

    public List<Bid> getBids(Item item) {
        Optional<AuctionData> auctionDataOpt = auctionDataRepository.findById(Long.valueOf(item.getId()));
        AuctionData auctionData = auctionDataOpt.get();
        List<Bid> bids = bidRepository.findByAuctionData(auctionData);
        return bids;
    }

    public String updatePrice(BidRequest bid) {
        Optional<AuctionData> auctionDataOpt = auctionDataRepository.findById(Long.valueOf(bid.getId()));
        AuctionData auctionData = auctionDataOpt.get();
        String price = bid.getBid();
        auctionData.setCurrentPrice(Double.valueOf(price));
        auctionDataRepository.save(auctionData);
        return price;
    }
}
