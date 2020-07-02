package by.itstep.onlineauctionsystem.websocket;

import by.itstep.onlineauctionsystem.service.AutoBiddingService;
import by.itstep.onlineauctionsystem.service.BiddingService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class WebSocketController {

    private final BiddingService biddingService;
    private final AutoBiddingService autoBiddingService;

    public WebSocketController(BiddingService biddingService, AutoBiddingService autoBiddingService) {
        this.biddingService = biddingService;
        this.autoBiddingService = autoBiddingService;
    }

    @MessageMapping("/bid-request")
    @SendTo("/topic/bid-response")
    public BidResponse placeBid(Principal principal, BidRequest bid) throws Exception {
        Thread.sleep(500);
        return biddingService.placeBid(principal, bid);
    }

    @MessageMapping("/auto-bid-request")
    @SendTo("/topic/bid-response")
    public BidResponse placeAutoBid(Principal principal, BidRequest bid) throws Exception {
        Thread.sleep(3500);
        return autoBiddingService.placeAutoBid(principal, bid);
    }
}
