package by.itstep.onlineauctionsystem.mapper;

import by.itstep.onlineauctionsystem.model.Item;
import by.itstep.onlineauctionsystem.model.item.ItemDto;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-04-02T16:21:15+0300",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.5 (JetBrains s.r.o)"
)
public class ItemMapperImpl implements ItemMapper {

    @Override
    public Item toItem(ItemDto itemDto) {
        if ( itemDto == null ) {
            return null;
        }

        Item item = new Item();

        return item;
    }

    @Override
    public ItemDto fromLot(Item item) {
        if ( item == null ) {
            return null;
        }

        ItemDto itemDto = new ItemDto();

        return itemDto;
    }
}
