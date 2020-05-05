package by.itstep.onlineauctionsystem.entity.bidding;

import by.itstep.onlineauctionsystem.entity.item.AuctionData;
import by.itstep.onlineauctionsystem.entity.user.User;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
public class AutoBid {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;
    private double maximumBid;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "auction_data_id")
    private AuctionData auctionData;
}
