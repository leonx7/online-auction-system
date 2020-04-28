package by.itstep.onlineauctionsystem.model.item;

import by.itstep.onlineauctionsystem.model.user.User;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@Table
@DynamicUpdate
public class Item {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;
    @ManyToOne
    private User creator;
    @OneToOne(mappedBy = "item", cascade= CascadeType.ALL)
    private ItemData itemData;
    @OneToOne(mappedBy = "item", cascade=CascadeType.ALL)
    private AuctionData auctionData;
}
