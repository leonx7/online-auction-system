package by.itstep.onlineauctionsystem.controller;

import by.itstep.onlineauctionsystem.dto.AutoBidDto;
import by.itstep.onlineauctionsystem.service.AutoBiddingService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class BiddingController {

    private final AutoBiddingService autoBiddingService;

    public BiddingController(AutoBiddingService autoBiddingService) {
        this.autoBiddingService = autoBiddingService;
    }

    @PostMapping("item/auto-bid")
    public ResponseEntity setupAutoBid(Principal principal, AutoBidDto autoBidDto){
        autoBiddingService.saveAutoBid(principal, autoBidDto);
        return ResponseEntity.ok().build();
    }
}
