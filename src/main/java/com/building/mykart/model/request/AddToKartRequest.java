package com.building.mykart.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class AddToKartRequest {
    private Long userId;
    private List<Map<String, Object>> items;
}
