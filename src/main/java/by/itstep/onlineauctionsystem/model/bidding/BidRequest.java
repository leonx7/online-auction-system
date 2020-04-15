package by.itstep.onlineauctionsystem.model.bidding;

import lombok.Data;

@Data
public class BidRequest {

    private String id;
    private String bid;
    private String increment;

    public BidRequest() {
    }

    public BidRequest(String id, String bid, String increment) {
        this.id = id;
        this.bid = bid;
        this.increment =increment;
    }
}
