package by.itstep.onlineauctionsystem.model.user;

import by.itstep.onlineauctionsystem.model.bidding.Bid;
import by.itstep.onlineauctionsystem.model.item.AuctionData;
import by.itstep.onlineauctionsystem.model.item.Item;
import lombok.Data;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Date createdAt = new Date();;
    @ManyToMany
    private Set<Role> roles;
    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, mappedBy = "creator")
    Set<Item> items;
    @OneToMany(mappedBy = "user")
    Set<Bid> bids;
    @OneToMany
    Set<AuctionData> winnings;

    public User() {
    }

    public User(UserDto userDto) {
        this.email = userDto.getEmail();
        this.password = userDto.getPassword();
    }
}
