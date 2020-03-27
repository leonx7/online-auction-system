package by.itstep.onlineauctionsystem.controller;

import by.itstep.onlineauctionsystem.model.Lot;
import by.itstep.onlineauctionsystem.model.Photo;
import by.itstep.onlineauctionsystem.service.LotService;
import by.itstep.onlineauctionsystem.service.PhotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Controller
public class LotController {

    @Autowired
    LotService lotService;
    @Autowired
    PhotoStorageService photoStorageService;

    @GetMapping("/lot/{id}")
    public String getLot(@PathVariable Long id, Model model) {
        Optional<Lot> lotOpt = lotService.getLot(id);
        if (lotOpt.isPresent()) {
            Lot lot = lotOpt.get();
            List<Photo> photos = photoStorageService.getPhotos(lot);
            List<String> encodedPhotos = new ArrayList<>();
            for (Photo photo : photos) {
                String encodedPhoto = Base64.getEncoder().encodeToString(photo.getData());
                encodedPhotos.add(encodedPhoto);
            }
            model.addAttribute("title", lot.getTitle());
            model.addAttribute("description", lot.getDescription());
            model.addAttribute("photos", encodedPhotos);
        }

        return "lot";
    }

    @GetMapping("/add")
    public String addLot(Model model) {
        model.addAttribute("lot", new Lot());
        return "add";
    }

    @PostMapping("/add")
    public String addLot(@ModelAttribute("lot") Lot lot, @RequestParam("files") MultipartFile[] files) {
        lotService.save(lot);
        photoStorageService.save(files, lot);
        return "redirect:/lot";
    }
}
