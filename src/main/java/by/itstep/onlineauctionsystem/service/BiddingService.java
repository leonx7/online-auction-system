package by.itstep.onlineauctionsystem.service;

import by.itstep.onlineauctionsystem.model.bidding.Bid;
import by.itstep.onlineauctionsystem.model.bidding.BidRequest;
import by.itstep.onlineauctionsystem.model.bidding.BidResponse;
import by.itstep.onlineauctionsystem.model.item.AuctionData;
import by.itstep.onlineauctionsystem.model.user.User;
import by.itstep.onlineauctionsystem.repository.AuctionDataRepository;
import by.itstep.onlineauctionsystem.repository.BidRepository;
import by.itstep.onlineauctionsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;
import java.util.Optional;

@Service
public class BiddingService {

    @Autowired
    BidRepository bidRepository;
    @Autowired
    AuctionDataRepository auctionDataRepository;
    @Autowired
    UserRepository userRepository;

    public BidResponse updateBid(BidRequest bid) throws InterruptedException {
        Thread.sleep(1000); // simulated delay
        Double currentBid = Double.valueOf(bid.getBid());
        Double increment = Double.valueOf(bid.getIncrement());
        Double nextBid = currentBid + increment;
        return new BidResponse(HtmlUtils.htmlEscape(String.valueOf(nextBid)));
    }

    public Bid saveBid(Principal principal, BidRequest bidRequest){
        User user = userRepository.findByEmail(principal.getName());
        Optional<AuctionData> auctionDataOpt = auctionDataRepository.findById(Long.valueOf(bidRequest.getId()));
        AuctionData auctionData = auctionDataOpt.get();
        Bid bid = new Bid();
        bid.setUser(user);
        bid.setAuctionData(auctionData);
        bidRepository.save(bid);
        return bid;
    }
}
