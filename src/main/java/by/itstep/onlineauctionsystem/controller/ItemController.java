package by.itstep.onlineauctionsystem.controller;

import by.itstep.onlineauctionsystem.model.category.Category;
import by.itstep.onlineauctionsystem.model.category.CategoryDto;
import by.itstep.onlineauctionsystem.model.item.Item;
import by.itstep.onlineauctionsystem.model.item.ItemDto;
import by.itstep.onlineauctionsystem.service.CategoryService;
import by.itstep.onlineauctionsystem.service.ItemService;
import by.itstep.onlineauctionsystem.service.ImageStorageService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    CategoryService categoryService;

    @GetMapping("/")
    public String getIndex(Model model) {
        List<ItemDto> items = itemService.getAllItems();
        List<CategoryDto> categories = categoryService.getCategories();
        model.addAttribute("items", items);
        model.addAttribute("categories", categories);
        return "index";
    }

    @GetMapping("/item/{id}")
    public String getItem(@PathVariable Long id, Model model) {
        ItemDto itemDto = itemService.getItem(id);
        model.addAttribute("itemDto", itemDto);
        return "item";
    }

    @GetMapping("/category/{id}")
    public String getItemsByCategory(@PathVariable Integer id, Model model){
        Category category = categoryService.getCategoryById(id);
        List<ItemDto> itemsByCategory = itemService.getItemsByCategory(category);
        List<CategoryDto> categories = categoryService.getCategories();
        model.addAttribute("items", itemsByCategory);
        model.addAttribute("categories", categories);
        return "index";
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
