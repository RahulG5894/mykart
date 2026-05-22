package com.building.mykart.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RemoveItemForm {
    private Long itemId;
    private Integer quantity;
    private Long userId;
}
