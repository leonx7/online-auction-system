package by.itstep.onlineauctionsystem.model.item;

import by.itstep.onlineauctionsystem.model.category.Category;
import lombok.Data;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.MapsId;
import java.util.Set;

@Entity
@Indexed
@Data
public class ItemData {
    @Id
    private long id;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category categoryId;
    @Field
    private String title;
    private String description;
    @OneToOne
    @MapsId
    private Item item;
    @OneToMany(mappedBy = "itemData")
    private Set<Image> images;
}
