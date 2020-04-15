package by.itstep.onlineauctionsystem.model.item;

import by.itstep.onlineauctionsystem.model.user.User;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Data
@Table
@DynamicUpdate
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private User creator;
    @OneToOne(mappedBy = "item",  cascade = CascadeType.ALL)
    private ItemData itemData;
    @OneToOne(mappedBy = "item",  cascade = CascadeType.ALL)
    private AuctionData auctionData;
}
