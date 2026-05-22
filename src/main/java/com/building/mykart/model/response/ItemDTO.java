package com.building.mykart.model.response;

import com.building.mykart.model.Item;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ItemDTO {
    private Long id;
    private String name;
    private BigDecimal price;
    private Double weight;
    private Integer quantity;

    public static ItemDTO convertToItemDTO(Item item, Integer quantity) {
        return ItemDTO.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .weight(item.getWeight())
                .quantity(quantity)
                .build();
    }
}
