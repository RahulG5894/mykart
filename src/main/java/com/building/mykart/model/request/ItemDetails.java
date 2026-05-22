package com.building.mykart.model.request;

import com.building.mykart.model.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class ItemDetails {
    private String name;
    private BigDecimal price;
    private Double weight;

    public Item toItem() {
        Item item = new Item();
        item.setName(this.name);
        item.setPrice(this.price);
        item.setWeight(this.weight);
        return item;
    }
}
