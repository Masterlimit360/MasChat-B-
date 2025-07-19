package com.postgresql.MasChat.controller;

import com.postgresql.MasChat.model.MarketplaceOrder;
import com.postgresql.MasChat.service.MarketplaceOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/marketplace/orders")
public class MarketplaceOrderController {
    @Autowired
    private MarketplaceOrderService orderService;

    @GetMapping
    public List<MarketplaceOrder> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MarketplaceOrder> getOrder(@PathVariable Long id) {
        return orderService.getOrder(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public MarketplaceOrder createOrder(@RequestBody MarketplaceOrder order) {
        return orderService.createOrder(order);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MarketplaceOrder> updateOrder(@PathVariable Long id, @RequestBody MarketplaceOrder order) {
        return ResponseEntity.ok(orderService.updateOrder(id, order));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buyer/{buyerId}")
    public List<MarketplaceOrder> getOrdersByBuyer(@PathVariable Long buyerId) {
        return orderService.getOrdersByBuyer(buyerId);
    }

    @GetMapping("/seller/{sellerId}")
    public List<MarketplaceOrder> getOrdersBySeller(@PathVariable Long sellerId) {
        return orderService.getOrdersBySeller(sellerId);
    }

    @GetMapping("/item/{itemId}")
    public List<MarketplaceOrder> getOrdersByItem(@PathVariable Long itemId) {
        return orderService.getOrdersByItem(itemId);
    }
} 