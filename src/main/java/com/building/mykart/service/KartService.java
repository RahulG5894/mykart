package com.building.mykart.service;

import com.building.mykart.model.*;
import com.building.mykart.model.request.AddToKartRequest;
import com.building.mykart.model.request.RemoveItemForm;
import com.building.mykart.model.response.ItemDTO;
import com.building.mykart.model.response.KartItemsDTO;
import com.building.mykart.notification.EmailService;
import com.building.mykart.repository.ItemRepository;
import com.building.mykart.repository.KartRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class KartService {

    private final ItemRepository itemRepository;
    private final KartRepository kartRepository;
    private final UserService userService;
    private final EmailService emailService;
    private final OrderService orderService;

    public List<Item> getItemsListInKart(Long userId) {
        userService.validateUser(userId);
        Kart kartItems = kartRepository.findByUserId(userId);
        if(kartItems == null) return null;
        List<Map<String, Object>> items = kartItems.getItems();
        List<Item> itemList = new ArrayList<>();
        for (Map<String, Object> item : items) {
            long itemId = Integer.parseInt(item.get("itemId").toString());
            itemList.add(itemRepository.getReferenceById(itemId));
        }
        return itemList;
    }

    public KartItemsDTO getItemsInKart(Long userId) {
        userService.validateUser(userId);
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

    @Transactional(rollbackOn = Exception.class)
    public String checkoutItemInKart(HttpServletRequest request) {
        User user = userService.getLoggedInUserDetail(request);
        KartItemsDTO kartItems = getItemsInKart(user.getId());
        if (kartItems == null || kartItems.getItems() == null || kartItems.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }
        orderService.createOrder(user, kartItems.getItems());
        emailService.sendOrderDone("viveksah59@gmail.com");
        return "order placed successfully!!";
    }
}
