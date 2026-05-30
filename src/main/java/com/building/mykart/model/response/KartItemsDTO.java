package com.building.mykart.model.response;

import com.building.mykart.model.Item;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class KartItemsDTO {
    private List<ItemDTO> items;
    private BigDecimal totalAmount;
}
