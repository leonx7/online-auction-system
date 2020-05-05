package by.itstep.onlineauctionsystem.entity.item;

import by.itstep.onlineauctionsystem.entity.user.User;
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
    private String buyerEmail;
    @OneToOne(mappedBy = "item", cascade= CascadeType.ALL)
    private ItemData itemData;
    @OneToOne(mappedBy = "item", cascade=CascadeType.ALL)
    private AuctionData auctionData;
}
