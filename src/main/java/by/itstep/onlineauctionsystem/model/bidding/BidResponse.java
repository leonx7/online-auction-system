package by.itstep.onlineauctionsystem.model.bidding;

import lombok.Data;

@Data
public class BidResponse {

    private String content;
    private String username;
    private String currentBid;

    public BidResponse() {
    }

    public BidResponse(String content, String username, String currentBid) {
        this.content = content;
        this.username = username;
        this.currentBid = currentBid;
    }
}
