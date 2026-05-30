package com.building.mykart.service;

import com.building.mykart.model.*;
import com.building.mykart.model.response.ItemDTO;
import com.building.mykart.repository.ItemRepository;
import com.building.mykart.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public Order createOrder(User user, List<ItemDTO> lineItems) {
        Order order = Order.builder()
                .user(user)
                .status(OrderStatus.CREATED)
                .shippingAddress(user.getAddress())
                .build();

        BigDecimal total = BigDecimal.ZERO;
        for (ItemDTO line : lineItems) {
            Item item = itemRepository.findById(line.getId())
                    .orElseThrow(() -> new RuntimeException("Item not found: " + line.getId()));

            OrderItem orderItem = OrderItem.builder()
                    .item(item)
                    .quantity(line.getQuantity())
                    .price(line.getPrice())
                    .build();
            order.addOrderItem(orderItem);

            total = total.add(line.getPrice().multiply(BigDecimal.valueOf(line.getQuantity())));
        }
        order.setTotalAmount(total);

        return orderRepository.save(order);
    }
}
