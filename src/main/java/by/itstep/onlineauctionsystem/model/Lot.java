package by.itstep.onlineauctionsystem.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table
public class Lot {
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
    private String description;
    @Getter
    @Setter
    @OneToMany(mappedBy = "lot")
    private Set<Photo> photos;
}
