package by.itstep.onlineauctionsystem.model.item;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Lob
    private byte[] data;
    @ManyToOne
    @JoinColumn(name = "item_id")
    private ItemData itemData;
}
