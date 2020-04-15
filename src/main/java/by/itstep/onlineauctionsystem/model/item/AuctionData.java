package by.itstep.onlineauctionsystem.model.item;

import by.itstep.onlineauctionsystem.model.bidding.Bid;
import by.itstep.onlineauctionsystem.model.user.User;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class AuctionData {
    @Id
    private long id;
    private double startPrice;
    private double currentPrice;
    private double increment;
    private String startTime;
    private String endTime;
    @ManyToOne
    private User auctionWinner;
    @OneToOne
    @MapsId
    private Item item;
    @OneToMany(mappedBy = "auctionData")
    Set<Bid> bids;

}
