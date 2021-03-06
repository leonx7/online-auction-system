package by.itstep.onlineauctionsystem.service;

import by.itstep.onlineauctionsystem.entity.item.Item;
import by.itstep.onlineauctionsystem.exeption.FileStorageException;
import by.itstep.onlineauctionsystem.dto.ItemDto;
import by.itstep.onlineauctionsystem.entity.item.Image;
import by.itstep.onlineauctionsystem.repository.ItemRepository;
import by.itstep.onlineauctionsystem.repository.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class ImageStorageService {

    final ImageRepository imageRepository;
    final ItemRepository itemRepository;

    public ImageStorageService(ImageRepository imageRepository, ItemRepository itemRepository) {
        this.imageRepository = imageRepository;
        this.itemRepository = itemRepository;
    }

    public void save(ItemDto itemDto, Item item) {
        MultipartFile[] files = itemDto.getImages();
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                try {
                    Image image = new Image();
                    image.setItem(item);
                    image.setTitle(file.getOriginalFilename());
                    image.setData(file.getBytes());
                    imageRepository.save(image);
                } catch (IOException e) {
                    throw new FileStorageException("Could not store file. Please try again!", e);
                }
            }
        }
    }

    public List<String> getPhotos(Long id) {
        Optional<Item> itemDataOptOpt = itemRepository.findById(id);
        Item item = itemDataOptOpt.get();
        List<Image> images = imageRepository.findByItem(item);
        List<String> encodedPhotos = new ArrayList<>();
        for (Image image : images) {
            String encodedPhoto = Base64.getEncoder().encodeToString(image.getData());
            encodedPhotos.add(encodedPhoto);
        }
        return encodedPhotos;
    }
}
