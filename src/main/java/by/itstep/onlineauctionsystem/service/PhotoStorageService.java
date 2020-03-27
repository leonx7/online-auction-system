package by.itstep.onlineauctionsystem.service;

import by.itstep.onlineauctionsystem.exeption.FileStorageException;
import by.itstep.onlineauctionsystem.model.Lot;
import by.itstep.onlineauctionsystem.model.Photo;
import by.itstep.onlineauctionsystem.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class PhotoStorageService {

    @Autowired
    PhotoRepository photoRepository;

    public void save(MultipartFile[] files, Lot lot){
        for(MultipartFile file : files){
            if(!file.isEmpty())
            {
                try {
                    Photo photo = new Photo();
                    photo.setLot(lot);
                    photo.setTitle(file.getOriginalFilename());
                    photo.setData(file.getBytes());
                    photoRepository.save(photo);
                } catch (IOException e) {
                    throw new FileStorageException("Could not store file. Please try again!", e);
                }
            }
        }
    }

    public List<Photo> getPhotos(Lot lot){
        List<Photo> photos = null;
        try {
            photos = photoRepository.findByLot(lot);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  photos;
    }
}
