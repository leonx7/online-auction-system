package by.itstep.onlineauctionsystem.repository;

import by.itstep.onlineauctionsystem.model.Lot;
import by.itstep.onlineauctionsystem.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    List<Photo> findByLot(Lot lot);
}
