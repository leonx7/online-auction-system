package by.itstep.onlineauctionsystem.service;

import by.itstep.onlineauctionsystem.entity.bidding.Bid;
import by.itstep.onlineauctionsystem.entity.category.Category;
import by.itstep.onlineauctionsystem.entity.item.AuctionData;
import by.itstep.onlineauctionsystem.entity.item.Item;
import by.itstep.onlineauctionsystem.entity.item.ItemData;
import by.itstep.onlineauctionsystem.dto.ItemDto;
import by.itstep.onlineauctionsystem.entity.user.User;
import by.itstep.onlineauctionsystem.repository.AuctionDataRepository;
import by.itstep.onlineauctionsystem.repository.ItemDataRepository;
import by.itstep.onlineauctionsystem.repository.ItemRepository;
import by.itstep.onlineauctionsystem.repository.UserRepository;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ItemService {

    @PersistenceContext
    EntityManager entityManager;

    final ItemRepository itemRepository;
    final ItemDataRepository itemDataRepository;
    final ImageStorageService imageStorageService;
    final AuctionDataRepository auctionDataRepository;
    final UserRepository userRepository;
    final BiddingService biddingService;

    public ItemService(ItemRepository itemRepository, ItemDataRepository itemDataRepository, ImageStorageService imageStorageService, AuctionDataRepository auctionDataRepository, UserRepository userRepository, BiddingService biddingService) {
        this.itemRepository = itemRepository;
        this.itemDataRepository = itemDataRepository;
        this.imageStorageService = imageStorageService;
        this.auctionDataRepository = auctionDataRepository;
        this.userRepository = userRepository;
        this.biddingService = biddingService;
    }

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

    public List<ItemDto> getItemsByCategory(Category category) {
        List<ItemData> results = itemDataRepository.findAllByCategoryId(category);
        List<ItemDto> itemsDto = new ArrayList<>();
        for (Item item : getItemsByItemData(results)) {
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
        List<Bid> bids = biddingService.getBids(item);
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
            itemDto.setEmail(item.getCreator().getEmail());
            itemDto.setBidCount(String.valueOf(bids.size()));
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println("ItemDto object is null");
        }
        return itemDto;
    }

    public List<ItemDto> getItemBySearchQuery(String searchQuery) throws InterruptedException {
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        fullTextEntityManager.createIndexer().startAndWait();
        QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder()
                .forEntity(ItemData.class)
                .get();
        org.apache.lucene.search.Query query = queryBuilder
                .keyword()
                .onField("title")
                .matching(searchQuery)
                .createQuery();
        org.hibernate.search.jpa.FullTextQuery jpaQuery
                = fullTextEntityManager.createFullTextQuery(query, ItemData.class);
        List<ItemData> results = jpaQuery.getResultList();
        List<ItemDto> itemsDto = new ArrayList<>();
        for (Item item : getItemsByItemData(results)) {
            ItemDto itemDto = createItemDto(item);
            itemsDto.add(itemDto);
        }
        return itemsDto;
    }

    public List<Item> getItemsByItemData(List<ItemData> itemDataList){
        List<Long> itemsId = new ArrayList<>();
        for (ItemData itemData : itemDataList) {
            itemsId.add(itemData.getId());
        }
        List<Item> items = itemRepository.findAllById(itemsId);
        return items;
    }

    public List<ItemDto> getItemsPurchasedByUser(Principal principal){
        List<Item> purchases = itemRepository.findItemByBuyer(principal.getName());
        List<ItemDto> itemsDto = new ArrayList<>();
        for (Item item : purchases) {
            ItemDto itemDto = createItemDto(item);
            itemsDto.add(itemDto);
        }
        return itemsDto;
    }
}
