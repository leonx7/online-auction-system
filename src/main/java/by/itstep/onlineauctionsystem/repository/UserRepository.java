package by.itstep.onlineauctionsystem.repository;

import by.itstep.onlineauctionsystem.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
