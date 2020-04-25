package by.itstep.onlineauctionsystem.model.bidding;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BidResponse {

    private String content;
    private String username;
    private String currentBid;
}
