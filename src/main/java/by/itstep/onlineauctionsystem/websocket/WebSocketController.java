package by.itstep.onlineauctionsystem.websocket;

import by.itstep.onlineauctionsystem.emailsending.EmailService;
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
    private final EmailService emailService;

    public WebSocketController(BiddingService biddingService, AutoBiddingService autoBiddingService, EmailService emailService) {
        this.biddingService = biddingService;
        this.autoBiddingService = autoBiddingService;
        this.emailService = emailService;
    }

    @MessageMapping("/bid-request")
    @SendTo("/topic/bid-response")
    public BidResponse placeBid(Principal principal, BidRequest bid) throws Exception {
        emailService.sendBidNotification(principal, bid);
        System.out.println("**************************************** Bid placed");
        return biddingService.placeBid(principal, bid);
    }

    @MessageMapping("/auto-bid-request")
    @SendTo("/topic/bid-response")
    public BidResponse placeAutoBid(Principal principal, BidRequest bid) throws Exception {
        Thread.sleep(4000);
        System.out.println("************************************************ AutoBid placed");
        return autoBiddingService.placeAutoBid(principal, bid);
    }
}
