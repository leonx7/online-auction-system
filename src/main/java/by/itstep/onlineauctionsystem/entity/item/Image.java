package by.itstep.onlineauctionsystem.entity.item;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Lob;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
public class Image {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String title;
    @Lob
    private byte[] data;
    @ManyToOne
    @JoinColumn(name = "item_id")
    private ItemData itemData;
}
