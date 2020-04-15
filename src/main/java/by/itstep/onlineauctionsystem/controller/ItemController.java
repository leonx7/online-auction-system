package by.itstep.onlineauctionsystem.controller;

import by.itstep.onlineauctionsystem.model.item.Item;
import by.itstep.onlineauctionsystem.model.item.ItemDto;
import by.itstep.onlineauctionsystem.model.user.User;
import by.itstep.onlineauctionsystem.service.ItemService;
import by.itstep.onlineauctionsystem.service.ImageStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class ItemController {

    @Autowired
    ItemService itemService;
    @Autowired
    ImageStorageService imageStorageService;

    @GetMapping("/")
    public String getIndex(Model model) {
        List<ItemDto> itemsDto = itemService.getAllItems();
        model.addAttribute("itemsDto", itemsDto);
        return "index";
    }

    @GetMapping("/item/{id}")
    public String getItem(@PathVariable Long id, Model model) {
        ItemDto itemDto = itemService.getItem(id);
        model.addAttribute("itemDto", itemDto);
        return "item";
    }

    @GetMapping("/add")
    public String addItem() {
        return "add";
    }

    @PostMapping("/add")
    public String addItem(Principal principal, @ModelAttribute("itemDto") ItemDto itemDto) {
        Item item = itemService.saveItem(itemDto, principal);
        return "redirect:/item/" + item.getId();
    }
}
