package by.itstep.onlineauctionsystem.entity.category;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;
    private String name;
    private int parentId;
    private int upperBound;
    private int lowerBound;
}
