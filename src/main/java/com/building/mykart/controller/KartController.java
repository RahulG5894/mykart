package com.building.mykart.controller;

import com.building.mykart.model.request.AddToKartRequest;
import com.building.mykart.model.request.RemoveItemForm;
import com.building.mykart.model.response.KartItemsDTO;
import com.building.mykart.service.KartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/kart")
@RequiredArgsConstructor
@Tag(name = "Kart Controller", description = "Operations related to Kart")
public class KartController {

    private final KartService kartService;

    @GetMapping("/items/{id}")
    @Operation(summary = "Get Items in Kart", description = "Fetches items in a kart")
    public KartItemsDTO getItemsInKart(@PathVariable("id") Long id) {
        return kartService.getItemsInKart(id);
    }

    // Add Item
    @PostMapping("/items/add")
    @Operation(summary = "Add Items in Kart", description = "Add items to kart")
    public ResponseEntity addItemToKart(@RequestBody AddToKartRequest request) {
        kartService.addItemToKart(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // Remove Item
    @DeleteMapping("/items/delete")
    @Operation(summary = "Remove Items from Kart", description = "Remove items from kart")
    public ResponseEntity removeItemFromKart(@RequestBody RemoveItemForm request) {
        kartService.removeItemFromKart(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
