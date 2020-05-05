package by.itstep.onlineauctionsystem.controller;

import by.itstep.onlineauctionsystem.entity.category.Category;
import by.itstep.onlineauctionsystem.dto.CategoryDto;
import by.itstep.onlineauctionsystem.entity.item.AuctionData;
import by.itstep.onlineauctionsystem.entity.item.Item;
import by.itstep.onlineauctionsystem.dto.ItemDto;
import by.itstep.onlineauctionsystem.entity.user.User;
import by.itstep.onlineauctionsystem.service.CategoryService;
import by.itstep.onlineauctionsystem.service.ItemService;
import by.itstep.onlineauctionsystem.service.UserService;
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

    private final ItemService itemService;
    private final CategoryService categoryService;
    private final UserService userService;

    public ItemController(ItemService itemService, CategoryService categoryService, UserService userService) {
        this.itemService = itemService;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String getIndex(Model model) {
        List<ItemDto> items = itemService.getAllItems();
        List<CategoryDto> categories = categoryService.getCategories();
        model.addAttribute("items", items);
        model.addAttribute("categories", categories);
        return "index";
    }

    @GetMapping("item/{id}")
    public String getItem(@PathVariable Long id, Model model) {
        ItemDto itemDto = itemService.getItem(id);
        model.addAttribute("itemDto", itemDto);
        return "item";
    }

    @GetMapping("category/{id}")
    public String getItemsByCategory(@PathVariable Integer id, Model model) {
        Category category = categoryService.getCategoryById(id);
        List<ItemDto> itemsByCategory = itemService.getItemsByCategory(category);
        List<CategoryDto> categories = categoryService.getCategories();
        model.addAttribute("items", itemsByCategory);
        model.addAttribute("categories", categories);
        return "index";
    }

    @GetMapping("item/add")
    public String addItem() {
        return "add";
    }

    @PostMapping("item/add")
    public String addItem(Principal principal, @ModelAttribute("itemDto") ItemDto itemDto) {
        Item item = itemService.saveItem(itemDto, principal);
        return "redirect:/item/" + item.getId();
    }

    @GetMapping("item/search")
    public String searchItem(@RequestParam("search")String search, Model model) throws InterruptedException {
        List<ItemDto> itemsBySearch = itemService.getItemBySearchQuery(search);
        List<CategoryDto> categories = categoryService.getCategories();
        model.addAttribute("items", itemsBySearch);
        model.addAttribute("categories", categories);
        return "index";
    }

    @GetMapping("user/purchases")
    public String showPurchases(Principal principal, Model model){
        List<ItemDto> purchases = itemService.getItemsPurchasedByUser(principal);
        model.addAttribute("purchases", purchases);
        return "purchases";
    }
}
