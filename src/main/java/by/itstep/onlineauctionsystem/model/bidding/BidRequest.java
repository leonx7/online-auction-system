package by.itstep.onlineauctionsystem.model.bidding;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BidRequest {

    private String id;
    private String bid;
    private String increment;
}
