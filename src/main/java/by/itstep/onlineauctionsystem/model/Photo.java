package by.itstep.onlineauctionsystem.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private String title;
    @Getter
    @Setter
    @Lob
    private byte[] data;
    @Getter
    @Setter
    @ManyToOne
    private Lot lot;

}
