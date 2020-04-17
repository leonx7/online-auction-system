package by.itstep.onlineauctionsystem.controller;

import by.itstep.onlineauctionsystem.model.bidding.BidRequest;
import by.itstep.onlineauctionsystem.model.bidding.BidResponse;
import by.itstep.onlineauctionsystem.model.user.User;
import by.itstep.onlineauctionsystem.service.BiddingService;
import by.itstep.onlineauctionsystem.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class BiddingController {

    @Autowired
    ItemService itemService;
    @Autowired
    BiddingService biddingService;

    @MessageMapping("/bidRequest")
    @SendTo("/topic/bidResponse")
    public BidResponse placeBid(Principal prinsipal, BidRequest bid) throws Exception {
        itemService.updatePrice(bid);
        biddingService.saveBid(prinsipal, bid);
        return biddingService.updateBid(prinsipal, bid);
    }
}
