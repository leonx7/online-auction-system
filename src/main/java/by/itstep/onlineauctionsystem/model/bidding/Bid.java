package by.itstep.onlineauctionsystem.model.bidding;

import by.itstep.onlineauctionsystem.model.item.AuctionData;
import by.itstep.onlineauctionsystem.model.item.Item;
import by.itstep.onlineauctionsystem.model.user.User;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "auction_data_id")
    private AuctionData auctionData;
}
