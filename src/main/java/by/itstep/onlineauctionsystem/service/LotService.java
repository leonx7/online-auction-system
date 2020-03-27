package by.itstep.onlineauctionsystem.service;

import by.itstep.onlineauctionsystem.model.Lot;
import by.itstep.onlineauctionsystem.repository.LotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LotService {

    @Autowired
    LotRepository lotRepository;

    public  Lot save(Lot lot){
        lotRepository.save(lot);
        return lot;
    }

    public Optional<Lot> getLot(Long id){
        Optional lotOpt = null;
        try {
            lotOpt = lotRepository.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lotOpt;
    }
}
