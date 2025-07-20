package com.postgresql.MasChat.service;

import com.postgresql.MasChat.model.MarketplaceItem;
import com.postgresql.MasChat.model.MarketplaceCategory;
import com.postgresql.MasChat.model.MarketplaceOrder;
import com.postgresql.MasChat.repository.MarketplaceItemRepository;
import com.postgresql.MasChat.repository.MarketplaceCategoryRepository;
import com.postgresql.MasChat.repository.MarketplaceOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MarketplaceService {
    @Autowired
    private MarketplaceItemRepository itemRepository;
    @Autowired
    private MarketplaceCategoryRepository categoryRepository;
    @Autowired
    private MarketplaceOrderRepository orderRepository;

    public List<MarketplaceItem> getAllItems() {
        return itemRepository.findAll();
    }

    public Optional<MarketplaceItem> getItem(Long id) {
        return itemRepository.findById(id);
    }

    public MarketplaceItem createItem(MarketplaceItem item) {
        return itemRepository.save(item);
    }

    public MarketplaceItem updateItem(Long id, MarketplaceItem updated) {
        return itemRepository.findById(id).map(item -> {
            item.setTitle(updated.getTitle());
            item.setDescription(updated.getDescription());
            item.setPrice(updated.getPrice());
            item.setNegotiable(updated.getNegotiable());
            item.setCategory(updated.getCategory());
            item.setCondition(updated.getCondition());
            item.setImages(updated.getImages());
            item.setDeliveryMethod(updated.getDeliveryMethod());
            item.setLocation(updated.getLocation());
            item.setStatus(updated.getStatus());
            item.setUpdatedAt(java.time.LocalDateTime.now());
            return itemRepository.save(item);
        }).orElseThrow();
    }

    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }

    public List<MarketplaceItem> searchItems(String keyword) {
        return itemRepository.findByTitleContainingIgnoreCase(keyword);
    }

    public List<MarketplaceItem> filterByCategory(Long categoryId) {
        return itemRepository.findByCategoryId(categoryId);
    }

    public List<MarketplaceItem> filterBySeller(Long sellerId) {
        return itemRepository.findBySellerId(sellerId);
    }

    public List<MarketplaceItem> filterByStatus(String status) {
        return itemRepository.findByStatus(status);
    }

    public List<MarketplaceCategory> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<MarketplaceCategory> getCategory(Long id) {
        return categoryRepository.findById(id);
    }

    public MarketplaceCategory createCategory(MarketplaceCategory category) {
        return categoryRepository.save(category);
    }

    public MarketplaceOrder createOrder(MarketplaceOrder order) {
        return orderRepository.save(order);
    }
} 