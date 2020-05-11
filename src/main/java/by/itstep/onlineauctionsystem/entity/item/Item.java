package by.itstep.onlineauctionsystem.entity.item;

import by.itstep.onlineauctionsystem.entity.category.Category;
import by.itstep.onlineauctionsystem.entity.user.User;
import lombok.Data;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@Indexed
public class Item {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;
    @Field
    private String title;
    private String description;
    private String buyerEmail;
    @OneToOne(mappedBy = "item", cascade=CascadeType.ALL)
    private AuctionData auctionData;
    @OneToMany(mappedBy = "item")
    private Set<Image> images;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category categoryId;
    @ManyToOne
    private User creator;
}
