package com.building.mykart.service;

import com.building.mykart.model.Item;
import com.building.mykart.model.request.ItemDetails;
import com.building.mykart.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public void addItem(ItemDetails item) {
        itemRepository.save(item.toItem());
    }

    public void removeItem(Long id) {
        itemRepository.deleteById(id);
    }

    public List<Item> getItemInInventory() {
        return itemRepository.findAll();
    }
}
