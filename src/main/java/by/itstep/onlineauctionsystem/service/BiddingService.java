package by.itstep.onlineauctionsystem.service;

import by.itstep.onlineauctionsystem.entity.bidding.AutoBid;
import by.itstep.onlineauctionsystem.entity.bidding.Bid;
import by.itstep.onlineauctionsystem.entity.item.AuctionData;
import by.itstep.onlineauctionsystem.entity.item.Item;
import by.itstep.onlineauctionsystem.entity.user.User;
import by.itstep.onlineauctionsystem.repository.AuctionDataRepository;
import by.itstep.onlineauctionsystem.repository.AutoBidRepository;
import by.itstep.onlineauctionsystem.repository.BidRepository;
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
public class BiddingService {

    private final BidRepository bidRepository;
    private final AuctionDataRepository auctionDataRepository;
    private final UserRepository userRepository;
    private final AutoBidRepository autoBidRepository;
    private final ItemRepository itemRepository;

    public BiddingService(BidRepository bidRepository, AuctionDataRepository auctionDataRepository, UserRepository userRepository, AutoBidRepository autoBidRepository, ItemRepository itemRepository) {
        this.bidRepository = bidRepository;
        this.auctionDataRepository = auctionDataRepository;
        this.userRepository = userRepository;
        this.autoBidRepository = autoBidRepository;
        this.itemRepository = itemRepository;
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
