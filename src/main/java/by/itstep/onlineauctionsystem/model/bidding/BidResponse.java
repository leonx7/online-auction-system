package by.itstep.onlineauctionsystem.model.bidding;

import lombok.Data;

@Data
public class BidResponse {

    private String content;

    public BidResponse() {
    }

    public BidResponse(String content) {
        this.content = content;
    }
}
