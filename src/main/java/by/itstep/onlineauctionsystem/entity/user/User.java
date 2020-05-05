package by.itstep.onlineauctionsystem.entity.user;

import by.itstep.onlineauctionsystem.dto.UserDto;
import by.itstep.onlineauctionsystem.entity.bidding.AutoBid;
import by.itstep.onlineauctionsystem.entity.bidding.Bid;
import by.itstep.onlineauctionsystem.entity.item.AuctionData;
import by.itstep.onlineauctionsystem.entity.item.Item;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
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
    private String firstName;
    private String lastName;
    @OneToMany(mappedBy = "creator")
    Set<Item> items;
    @OneToMany(mappedBy = "user")
    Set<Bid> bids;
    @OneToMany(mappedBy = "user")
    Set<AutoBid> autoBids;

    public User() {
    }

    public User(UserDto userDto) {
        this.email = userDto.getEmail();
        this.password = userDto.getPassword();
    }
}
