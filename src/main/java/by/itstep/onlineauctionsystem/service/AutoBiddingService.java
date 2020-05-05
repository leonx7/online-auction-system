package by.itstep.onlineauctionsystem.service;

import by.itstep.onlineauctionsystem.dto.AutoBidDto;
import by.itstep.onlineauctionsystem.emailsending.EmailService;
import by.itstep.onlineauctionsystem.entity.bidding.AutoBid;
import by.itstep.onlineauctionsystem.entity.item.AuctionData;
import by.itstep.onlineauctionsystem.entity.user.User;
import by.itstep.onlineauctionsystem.repository.AuctionDataRepository;
import by.itstep.onlineauctionsystem.repository.AutoBidRepository;
import by.itstep.onlineauctionsystem.repository.ItemRepository;
import by.itstep.onlineauctionsystem.repository.UserRepository;
import by.itstep.onlineauctionsystem.websocket.BidRequest;
import by.itstep.onlineauctionsystem.websocket.BidResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class AutoBiddingService {

    private final UserRepository userRepository;
    private final AuctionDataRepository auctionDataRepository;
    private final AutoBidRepository autoBidRepository;
    private final BiddingService biddingService;
    private final ItemRepository itemRepository;
    private final EmailService emailService;

    public AutoBiddingService(UserRepository userRepository, AuctionDataRepository auctionDataRepository, AutoBidRepository autoBidRepository, BiddingService biddingService, ItemRepository itemRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.auctionDataRepository = auctionDataRepository;
        this.autoBidRepository = autoBidRepository;
        this.biddingService = biddingService;
        this.itemRepository = itemRepository;
        this.emailService = emailService;
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

        Optional<AuctionData> auctionDataOpt = auctionDataRepository.findById(Long.valueOf(bid.getId()));
        AuctionData auctionData = auctionDataOpt.get();
        List<AutoBid> autoBids = autoBidRepository.findByAuctionData(auctionData);
        if (!autoBids.isEmpty()) {
            AutoBid maxAutoBid = autoBidRepository.findAutoBidWithMaximumBid(auctionData);
            String username = maxAutoBid.getUser().getEmail();
            if (!principal.getName().equals(username) && maxAutoBid.getMaximumBid() > Double.valueOf(bid.getBid())) {
                Double currentBid = Double.valueOf(bid.getBid()) + Double.valueOf(bid.getIncrement());
                bid.setBid(String.valueOf(currentBid));
                biddingService.updatePrice(bid);
                biddingService.saveBid(username, bid);
                Double nextBid = Double.valueOf(bid.getBid()) + Double.valueOf(bid.getIncrement());
                emailService.sendAutoBidNotification(bid, username);
                return new BidResponse(HtmlUtils.htmlEscape(String.valueOf(nextBid)), HtmlUtils.htmlEscape(username), HtmlUtils.htmlEscape(bid.getBid()));
            }
        }
        return null;
    }
}
