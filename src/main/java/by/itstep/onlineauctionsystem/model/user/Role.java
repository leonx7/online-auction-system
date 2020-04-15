package by.itstep.onlineauctionsystem.model.user;

import by.itstep.onlineauctionsystem.model.user.User;
import lombok.Data;
import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "role")
@Data
public class Role {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String name;
        @ManyToMany(mappedBy = "roles")
        private Set<User> users;
}
