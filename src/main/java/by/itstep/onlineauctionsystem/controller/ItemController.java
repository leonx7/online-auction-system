package by.itstep.onlineauctionsystem.controller;

import by.itstep.onlineauctionsystem.model.category.Category;
import by.itstep.onlineauctionsystem.model.category.CategoryDto;
import by.itstep.onlineauctionsystem.model.item.Item;
import by.itstep.onlineauctionsystem.model.item.ItemDto;
import by.itstep.onlineauctionsystem.service.CategoryService;
import by.itstep.onlineauctionsystem.service.ItemService;
import by.itstep.onlineauctionsystem.service.ImageStorageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class ItemController {

    final ItemService itemService;
    final ImageStorageService imageStorageService;
    final CategoryService categoryService;

    public ItemController(ItemService itemService, ImageStorageService imageStorageService, CategoryService categoryService) {
        this.itemService = itemService;
        this.imageStorageService = imageStorageService;
        this.categoryService = categoryService;
    }

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
    public String getItemsByCategory(@PathVariable Integer id, Model model) {
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

    @GetMapping("/search")
    public String searchItem(@RequestParam("search")String search, Model model) throws InterruptedException {
        List<ItemDto> itemsBySearch = itemService.getItemBySearchQuery(search);
        List<CategoryDto> categories = categoryService.getCategories();
        model.addAttribute("items", itemsBySearch);
        model.addAttribute("categories", categories);
        return "index";
    }
}
