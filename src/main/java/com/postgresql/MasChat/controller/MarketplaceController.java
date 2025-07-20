package com.postgresql.MasChat.controller;

import com.postgresql.MasChat.model.MarketplaceItem;
import com.postgresql.MasChat.model.MarketplaceCategory;
import com.postgresql.MasChat.model.MarketplaceOrder;
import com.postgresql.MasChat.service.MarketplaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import com.postgresql.MasChat.model.User;
import com.postgresql.MasChat.repository.MarketplaceItemRepository;
import com.postgresql.MasChat.repository.UserRepository;

class MarketplaceOrderRequest {
    public Long itemId;
    public Long buyerId;
    public Long sellerId;
    public Double price;
    public String status;
    public String deliveryMethod;
    public String paymentMethod;
    public Double fee;
}

@RestController
@RequestMapping("/api/marketplace")
public class MarketplaceController {
    @Autowired
    private MarketplaceService marketplaceService;
    @Autowired
    private MarketplaceItemRepository itemRepository;
    @Autowired
    private UserRepository userRepository;

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

    @PostMapping("/upload-image")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String imageUrl = saveImage(file, "marketplace");
            return ResponseEntity.ok(imageUrl);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to upload image");
        }
    }

    private String saveImage(MultipartFile file, String type) throws IOException {
        String uploadDir = "uploads/";
        Files.createDirectories(Paths.get(uploadDir));
        String originalFileName = file.getOriginalFilename();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String fileName = type + "_" + UUID.randomUUID() + fileExtension;
        Path filePath = Paths.get(uploadDir + fileName);
        Files.write(filePath, file.getBytes());
        return "http://10.132.74.85:8080/uploads/" + fileName;
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

    @PostMapping("/orders")
    public ResponseEntity<MarketplaceOrder> createOrder(@RequestBody MarketplaceOrderRequest req) {
        MarketplaceOrder order = new MarketplaceOrder();
        order.setItem(itemRepository.findById(req.itemId).orElseThrow());
        order.setBuyer(userRepository.findById(req.buyerId).orElseThrow());
        order.setSeller(userRepository.findById(req.sellerId).orElseThrow());
        order.setPrice(req.price);
        order.setStatus(req.status);
        order.setDeliveryMethod(req.deliveryMethod);
        order.setPaymentMethod(req.paymentMethod);
        order.setFee(req.fee);
        MarketplaceOrder created = marketplaceService.createOrder(order);
        return ResponseEntity.status(201).body(created);
    }
} 