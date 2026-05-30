package com.building.mykart.service;

import com.building.mykart.model.Item;
import com.building.mykart.model.User;
import com.building.mykart.model.request.ItemDetails;
import com.building.mykart.model.response.ItemListDTO;
import com.building.mykart.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserService userService;

    public void addItem(ItemDetails itemDetails) {
        User seller = userService.getUserByID(itemDetails.getSeller());
        Item item = itemDetails.toItem();
        item.setSeller(seller);
        itemRepository.save(item);
    }

    public void removeItem(Long id) {
        itemRepository.deleteById(id);
    }

    public List<ItemListDTO> getItemInInventory() {
        List<Item> items = itemRepository.findAll();
        List<ItemListDTO> itemList = new ArrayList<>();
        for(Item item: items) {
            itemList.add(ItemListDTO.convertToItemListDTO(item));
        }
        return itemList;
    }
}
