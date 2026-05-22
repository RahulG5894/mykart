package com.building.mykart.service;

import com.building.mykart.model.Item;
import com.building.mykart.model.Kart;
import com.building.mykart.model.request.AddToKartRequest;
import com.building.mykart.model.request.RemoveItemForm;
import com.building.mykart.model.response.ItemDTO;
import com.building.mykart.model.response.KartItemsDTO;
import com.building.mykart.repository.ItemRepository;
import com.building.mykart.repository.KartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class KartService {
    private final ItemRepository itemRepository;
    private final KartRepository kartRepository;
    private final UserService userService;

    public KartItemsDTO getItemsInKart(Long userId) {
        if(!userService.validateUser(userId)) {
            throw new RuntimeException("User is undefined!");
        }
        Kart kartItems = kartRepository.findByUserId(userId);
        if(kartItems == null) return null;
        List<Map<String, Object>> items = kartItems.getItems();
        List<ItemDTO> itemList = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;
        for(Iterator<Map<String, Object>> iter = items.iterator(); iter.hasNext();) {
            Map<String, Object> item = iter.next();
            long itemId = Integer.parseInt(item.get("itemId").toString());
            int quantity = Integer.parseInt(item.get("quantity").toString());
            Item prod = itemRepository.getReferenceById(itemId);
            totalAmount = totalAmount.add(prod.getPrice().multiply(BigDecimal.valueOf(quantity)));
            itemList.add(ItemDTO.convertToItemDTO(prod, quantity));
        }
        return KartItemsDTO.builder().items(itemList).totalAmount(totalAmount).build();
    }

    public void addItemToKart(AddToKartRequest request) {
        if(!userService.validateUser(request.getUserId())) {
            throw new RuntimeException("User is undefined!");
        }
        Kart kart = kartRepository.findByUserId(request.getUserId());
        boolean existingUser = ObjectUtils.isNotEmpty(kart);
        if(!existingUser) {
            kart = new Kart();
            kart.setItems(new ArrayList<>());
        }
        List<Map<String, Object>> kartItems = kart.getItems();
        for (var item : request.getItems()) {
            if(existingUser && addToExistingItemsInKart(kartItems, item)) {
                continue;
            }
            kartItems.add(Map.of(
                    "itemId", item.get("itemId"),
                    "quantity", item.get("quantity")
            ));
        }
        kart.setItems(kartItems);
        kart.setUserId(request.getUserId());
        kartRepository.save(kart);
    }

    private boolean addToExistingItemsInKart(List<Map<String, Object>> items, Map<String, Object> item) {
        for(Iterator<Map<String, Object>> iterator = items.iterator(); iterator.hasNext();) {
            Map<String, Object> currItems = iterator.next();
            if(Long.parseLong(currItems.get("itemId").toString()) == Long.parseLong(item.get("itemId").toString())) {
                int currQty = Integer.parseInt(currItems.get("quantity").toString());
                int addQty = Integer.parseInt(item.get("quantity").toString());
                currItems.put("quantity", currQty+addQty);
                return true;
            }
        }
        return false;
    }

    public void removeItemFromKart(RemoveItemForm request) {
        if(!userService.validateUser(request.getUserId())) {
            throw new RuntimeException("User is undefined!");
        }
        Kart kartItems = kartRepository.findByUserId(request.getUserId());
        List<Map<String, Object>> items = kartItems.getItems();
        for(Iterator<Map<String, Object>> iterator = items.iterator(); iterator.hasNext();) {
            Map<String, Object> item = iterator.next();
            if(Long.parseLong(item.get("itemId").toString()) == request.getItemId()) {
                int currQty = Integer.parseInt(item.get("quantity").toString());
                if(currQty <= request.getQuantity()) {
                    iterator.remove();
                } else {
                    item.put("quantity", currQty-request.getQuantity());
                }
            }
        }
        kartItems.setItems(items);
        if(kartItems.getItems().size() == 0) {
            log.debug("deleting the kart items...");
            kartRepository.delete(kartItems);
            return;
        }
        log.debug("saving the kart items...");
        kartRepository.save(kartItems);
    }

}
