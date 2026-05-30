package com.building.mykart.model.response;

import com.building.mykart.model.Item;
import com.building.mykart.model.User;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ItemListDTO {
    private Long id;
    private String name;
    private BigDecimal price;
    private Double weight;

    public static ItemListDTO convertToItemListDTO(Item item) {
        return ItemListDTO.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .weight(item.getWeight())
                .build();
    }

}