package com.building.mykart.controller;

import com.building.mykart.model.Item;
import com.building.mykart.model.request.ItemDetails;
import com.building.mykart.model.response.ItemDTO;
import com.building.mykart.model.response.ItemListDTO;
import com.building.mykart.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1/item")
@RequiredArgsConstructor
@Tag(name = "Items Controller", description = "Operations related to Items")
public class ItemController {

    private final ItemService itemService;

    // Add Item
    @GetMapping
    @Operation(summary = "Get Items in Inventory", description = "Get Items in Inventory")
    public List<ItemListDTO> getItemInInventory() {
        return itemService.getItemInInventory();
    }

    // Add Item
    @PostMapping("/add")
    @Operation(summary = "Add Items to Inventory", description = "Add items to Inventory")
    public ResponseEntity addItem(@RequestBody ItemDetails itemDetails) {
        itemService.addItem(itemDetails);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // Remove Item
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Remove Items from Inventory", description = "Remove items from Inventory")
    public ResponseEntity removeItem(@RequestParam("id") Long id) {
        itemService.removeItem(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
