package by.itstep.onlineauctionsystem.controller;

import by.itstep.onlineauctionsystem.model.bidding.AutoBidDto;
import by.itstep.onlineauctionsystem.model.bidding.BidRequest;
import by.itstep.onlineauctionsystem.model.bidding.BidResponse;
import by.itstep.onlineauctionsystem.service.AutoBiddingService;
import by.itstep.onlineauctionsystem.service.BiddingService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.transaction.Transactional;
import java.security.Principal;

@Controller
public class BiddingController {

    final BiddingService biddingService;
    final AutoBiddingService autoBiddingService;

    public BiddingController(BiddingService biddingService, AutoBiddingService autoBiddingService) {
        this.biddingService = biddingService;
        this.autoBiddingService = autoBiddingService;
    }

    @MessageMapping("/bidRequest")
    @SendTo("/topic/bidResponse")
    public BidResponse placeBid(Principal principal, BidRequest bid) throws Exception {
        return biddingService.placeBid(principal, bid);
    }

    @MessageMapping("/autoBidRequest")
    @SendTo("/topic/bidResponse")
    @Transactional
    public BidResponse placeAutoBid(Principal principal, BidRequest bid) throws Exception {
        return autoBiddingService.placeAutoBid(principal, bid);
    }

    @PostMapping("/autoBid")
    public ResponseEntity setupAutoBid(Principal principal, AutoBidDto autoBidDto){
        autoBiddingService.saveAutoBid(principal, autoBidDto);
        return ResponseEntity.ok().build();
    }
}
