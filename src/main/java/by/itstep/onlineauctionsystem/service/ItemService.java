package by.itstep.onlineauctionsystem.service;

import by.itstep.onlineauctionsystem.model.bidding.BidRequest;
import by.itstep.onlineauctionsystem.model.item.AuctionData;
import by.itstep.onlineauctionsystem.model.item.Item;
import by.itstep.onlineauctionsystem.model.item.ItemData;
import by.itstep.onlineauctionsystem.model.item.ItemDto;
import by.itstep.onlineauctionsystem.model.user.User;
import by.itstep.onlineauctionsystem.repository.AuctionDataRepository;
import by.itstep.onlineauctionsystem.repository.ItemDataRepository;
import by.itstep.onlineauctionsystem.repository.ItemRepository;
import by.itstep.onlineauctionsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ItemDataRepository itemDataRepository;
    @Autowired
    ImageStorageService imageStorageService;
    @Autowired
    AuctionDataRepository auctionDataRepository;
    @Autowired
    UserRepository userRepository;

    public Item saveItem(ItemDto itemDto, Principal principal) {
        User user = userRepository.findByEmail(principal.getName());
        Item item = new Item();
        ItemData itemData = new ItemData();
        itemData.setItem(item);
        itemData.setTitle(itemDto.getTitle());
        itemData.setDescription(itemDto.getDescription());
        AuctionData auctionData = new AuctionData();
        auctionData.setItem(item);
        auctionData.setStartPrice(Double.valueOf(itemDto.getPrice()));
        auctionData.setCurrentPrice(Double.valueOf(itemDto.getPrice()));
        auctionData.setIncrement(Double.valueOf(itemDto.getIncrement()));
        auctionData.setEndTime(itemDto.getEndTime());
        item.setCreator(user);
        item.setItemData(itemData);
        item.setAuctionData(auctionData);
        itemRepository.save(item);
        imageStorageService.save(itemDto, itemData);
        return item;
    }

    public ItemDto getItem(Long id) {

        Optional<Item> itemOpt = itemRepository.findById(id);
        Item item = itemOpt.get();


        ItemDto itemDto = createItemDto(item);
        return itemDto;
    }

    public List<ItemDto> getAllItems() {
        List<Item> items = itemRepository.findAll();
        List<ItemDto> itemsDto = new ArrayList<>();
        for (Item item : items) {
            ItemDto itemDto = createItemDto(item);
            itemsDto.add(itemDto);
        }
        return itemsDto;
    }

    public String getFormattedDate(Item item) {
        LocalDateTime ldt = LocalDateTime.parse(item.getAuctionData().getEndTime());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String output = ldt.format(formatter);
        return output;
    }

    public ItemDto createItemDto(Item item) {
        Optional<ItemData> itemDataOpt = itemDataRepository.findById(item.getId());
        ItemData itemData = itemDataOpt.get();
        List<String> images = imageStorageService.getPhotos(item.getId());
        ItemDto itemDto = new ItemDto();
        try {
            itemDto.setId(item.getId());
            itemDto.setTitle(itemData.getTitle());
            itemDto.setDescription(itemData.getDescription());
            itemDto.setIncrement(Double.toString(item.getAuctionData().getIncrement()));
            itemDto.setPrice(Double.toString(item.getAuctionData().getCurrentPrice()));
            itemDto.setEndTime(item.getAuctionData().getEndTime());
            itemDto.setEncodedImages(images);
            itemDto.setFormattedEndTime(getFormattedDate(item));
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println("ItemDto object is null");
        }
        return itemDto;
    }

    public String updatePrice(BidRequest bid) {
        Optional<AuctionData> auctionDataOpt = auctionDataRepository.findById(Long.valueOf(bid.getId()));
        AuctionData auctionData = auctionDataOpt.get();
        String price = bid.getBid();
        auctionData.setCurrentPrice(Double.valueOf(price));
        auctionDataRepository.save(auctionData);
        return price;
    }
}
