package by.itstep.onlineauctionsystem.model.user;

import by.itstep.onlineauctionsystem.model.bidding.AutoBid;
import by.itstep.onlineauctionsystem.model.bidding.Bid;
import by.itstep.onlineauctionsystem.model.item.AuctionData;
import by.itstep.onlineauctionsystem.model.item.Item;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;
    private String email;
    private String password;
    @OneToMany(mappedBy = "creator")
    Set<Item> items;
    @OneToMany(mappedBy = "user")
    Set<Bid> bids;
    @OneToMany(mappedBy = "user")
    Set<AutoBid> autoBids;
    @OneToMany(mappedBy = "auctionWinner")
    Set<AuctionData> winnings;

    public User() {
    }

    public User(UserDto userDto) {
        this.email = userDto.getEmail();
        this.password = userDto.getPassword();
    }
}
