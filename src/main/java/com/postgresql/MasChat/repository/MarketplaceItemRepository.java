package com.postgresql.MasChat.repository;

import com.postgresql.MasChat.model.MarketplaceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MarketplaceItemRepository extends JpaRepository<MarketplaceItem, Long> {
    List<MarketplaceItem> findByCategoryId(Long categoryId);
    List<MarketplaceItem> findBySellerId(Long sellerId);
    List<MarketplaceItem> findByStatus(String status);
    List<MarketplaceItem> findByTitleContainingIgnoreCase(String keyword);
} 