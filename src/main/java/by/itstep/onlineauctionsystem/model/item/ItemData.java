package by.itstep.onlineauctionsystem.model.item;

import by.itstep.onlineauctionsystem.model.category.Category;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class ItemData {
    @Id
    private long id;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category categoryId;
    private String title;
    private String description;
    @OneToOne
    @MapsId
    private Item item;
    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, mappedBy = "itemData")
    private Set<Image> images;
}
