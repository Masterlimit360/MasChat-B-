package com.postgresql.MasChat.controller;

import com.postgresql.MasChat.model.MarketplaceItem;
import com.postgresql.MasChat.model.MarketplaceCategory;
import com.postgresql.MasChat.service.MarketplaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/marketplace")
public class MarketplaceController {
    @Autowired
    private MarketplaceService marketplaceService;

    // --- Items ---
    @GetMapping("/items")
    public List<MarketplaceItem> getAllItems() {
        return marketplaceService.getAllItems();
    }

    @GetMapping("/items/{id}")
    public ResponseEntity<MarketplaceItem> getItem(@PathVariable Long id) {
        return marketplaceService.getItem(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/items")
    public MarketplaceItem createItem(@RequestBody MarketplaceItem item) {
        return marketplaceService.createItem(item);
    }

    @PutMapping("/items/{id}")
    public ResponseEntity<MarketplaceItem> updateItem(@PathVariable Long id, @RequestBody MarketplaceItem item) {
        return ResponseEntity.ok(marketplaceService.updateItem(id, item));
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        marketplaceService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/items/search")
    public List<MarketplaceItem> searchItems(@RequestParam String keyword) {
        return marketplaceService.searchItems(keyword);
    }

    @GetMapping("/items/category/{categoryId}")
    public List<MarketplaceItem> filterByCategory(@PathVariable Long categoryId) {
        return marketplaceService.filterByCategory(categoryId);
    }

    @GetMapping("/items/seller/{sellerId}")
    public List<MarketplaceItem> filterBySeller(@PathVariable Long sellerId) {
        return marketplaceService.filterBySeller(sellerId);
    }

    @GetMapping("/items/status/{status}")
    public List<MarketplaceItem> filterByStatus(@PathVariable String status) {
        return marketplaceService.filterByStatus(status);
    }

    // --- Categories ---
    @GetMapping("/categories")
    public List<MarketplaceCategory> getAllCategories() {
        return marketplaceService.getAllCategories();
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<MarketplaceCategory> getCategory(@PathVariable Long id) {
        return marketplaceService.getCategory(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/categories")
    public MarketplaceCategory createCategory(@RequestBody MarketplaceCategory category) {
        return marketplaceService.createCategory(category);
    }
} 