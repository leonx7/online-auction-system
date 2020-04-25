package by.itstep.onlineauctionsystem.service;

import by.itstep.onlineauctionsystem.model.bidding.AutoBid;
import by.itstep.onlineauctionsystem.model.bidding.AutoBidDto;
import by.itstep.onlineauctionsystem.model.bidding.BidRequest;
import by.itstep.onlineauctionsystem.model.bidding.BidResponse;
import by.itstep.onlineauctionsystem.model.item.AuctionData;
import by.itstep.onlineauctionsystem.model.user.User;
import by.itstep.onlineauctionsystem.repository.AuctionDataRepository;
import by.itstep.onlineauctionsystem.repository.AutoBidRepository;
import by.itstep.onlineauctionsystem.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;
import java.util.Optional;

@Service
public class AutoBiddingService {

    final UserRepository userRepository;
    final AuctionDataRepository auctionDataRepository;
    final AutoBidRepository autoBidRepository;
    final BiddingService biddingService;

    public AutoBiddingService(UserRepository userRepository, AuctionDataRepository auctionDataRepository, AutoBidRepository autoBidRepository, BiddingService biddingService) {
        this.userRepository = userRepository;
        this.auctionDataRepository = auctionDataRepository;
        this.autoBidRepository = autoBidRepository;
        this.biddingService = biddingService;
    }

    public AutoBid saveAutoBid(Principal principal, AutoBidDto autoBidDto) {
        User user = userRepository.findByEmail(principal.getName());
        Optional<AuctionData> auctionDataOpt = auctionDataRepository.findById(Long.valueOf(autoBidDto.getId()));
        AuctionData auctionData = auctionDataOpt.get();
        AutoBid autoBid = new AutoBid();
        autoBid.setUser(user);
        autoBid.setAuctionData(auctionData);
        autoBid.setMaximumBid(Long.valueOf(autoBidDto.getMaximumBid()));
        return autoBidRepository.save(autoBid);
    }

    public BidResponse placeAutoBid(Principal principal, BidRequest bid) throws InterruptedException {
        Thread.sleep(2000);
        AutoBid maxAutoBid = autoBidRepository.findAutoBidWithMaximumBid();
        String username = maxAutoBid.getUser().getEmail();
        if(!principal.getName().equals(username) && maxAutoBid.getMaximumBid() > Double.valueOf(bid.getBid())){
            Double currentBid = Double.valueOf(bid.getBid()) + Double.valueOf(bid.getIncrement());
            bid.setBid(String.valueOf(currentBid));
            biddingService.updatePrice(bid);
            biddingService.saveBid(username, bid);
            Double nextBid = Double.valueOf(bid.getBid()) + Double.valueOf(bid.getIncrement());
            return new BidResponse(HtmlUtils.htmlEscape(String.valueOf(nextBid)), HtmlUtils.htmlEscape(username), HtmlUtils.htmlEscape(bid.getBid()));
        }
        return null;
    }
}
