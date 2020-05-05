package by.itstep.onlineauctionsystem.entity.item;

import by.itstep.onlineauctionsystem.entity.bidding.Bid;
import by.itstep.onlineauctionsystem.entity.user.User;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.MapsId;
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
    @OneToOne
    @MapsId
    private Item item;
    @OneToMany(mappedBy = "auctionData")
    Set<Bid> bids;
    @OneToMany(mappedBy = "auctionData")
    Set<Bid> autoBids;
}
