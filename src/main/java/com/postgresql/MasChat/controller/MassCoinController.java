package com.postgresql.MasChat.controller;

import com.postgresql.MasChat.dto.MassCoinDTO;
import com.postgresql.MasChat.service.MassCoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/masscoin")
@CrossOrigin(origins = "*")
public class MassCoinController {
    
    @Autowired
    private MassCoinService massCoinService;
    
    // Wallet Endpoints
    
    @GetMapping("/wallet")
    public ResponseEntity<?> getWallet(Authentication authentication) {
        try {
            Long userId = Long.valueOf(authentication.getName());
            MassCoinDTO.WalletInfo wallet = massCoinService.getWallet(userId);
            return ResponseEntity.ok(wallet);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @PostMapping("/wallet/address")
    public ResponseEntity<?> updateWalletAddress(
            Authentication authentication,
            @RequestBody Map<String, String> request) {
        try {
            Long userId = Long.valueOf(authentication.getName());
            String newAddress = request.get("walletAddress");
            
            if (newAddress == null || newAddress.trim().isEmpty()) {
                throw new RuntimeException("Wallet address is required");
            }
            
            MassCoinDTO.WalletInfo wallet = massCoinService.updateWalletAddress(userId, newAddress);
            return ResponseEntity.ok(wallet);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    // Transaction Endpoints
    
    @PostMapping("/transfer")
    public ResponseEntity<?> transferMass(
            Authentication authentication,
            @RequestBody MassCoinDTO.TransferRequest request) {
        try {
            Long senderId = Long.valueOf(authentication.getName());
            
            if (request.getRecipientId() == null || request.getRecipientId().trim().isEmpty()) {
                throw new RuntimeException("Recipient ID is required");
            }
            
            if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
                throw new RuntimeException("Valid amount is required");
            }
            
            MassCoinDTO.TransactionInfo transaction = massCoinService.transferMass(senderId, request);
            return ResponseEntity.ok(transaction);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @PostMapping("/tip")
    public ResponseEntity<?> tipCreator(
            Authentication authentication,
            @RequestBody Map<String, Object> request) {
        try {
            Long senderId = Long.valueOf(authentication.getName());
            String postId = (String) request.get("postId");
            BigDecimal amount = new BigDecimal(request.get("amount").toString());
            String description = (String) request.get("description");
            
            if (postId == null || postId.trim().isEmpty()) {
                throw new RuntimeException("Post ID is required");
            }
            
            if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new RuntimeException("Valid amount is required");
            }
            
            MassCoinDTO.TransactionInfo transaction = massCoinService.tipCreator(senderId, postId, amount, description);
            return ResponseEntity.ok(transaction);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @PostMapping("/reward")
    public ResponseEntity<?> rewardUser(
            Authentication authentication,
            @RequestBody Map<String, Object> request) {
        try {
            Long userId = Long.valueOf((String) request.get("userId"));
            BigDecimal amount = new BigDecimal(request.get("amount").toString());
            String reason = (String) request.get("reason");
            
            if (userId == null) {
                throw new RuntimeException("User ID is required");
            }
            
            if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new RuntimeException("Valid amount is required");
            }
            
            MassCoinDTO.TransactionInfo transaction = massCoinService.rewardUser(userId, amount, reason);
            return ResponseEntity.ok(transaction);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    // Staking Endpoints
    
    @PostMapping("/stake")
    public ResponseEntity<?> stakeMass(
            Authentication authentication,
            @RequestBody MassCoinDTO.StakingRequest request) {
        try {
            Long userId = Long.valueOf(authentication.getName());
            
            if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
                throw new RuntimeException("Valid amount is required");
            }
            
            if (!"stake".equals(request.getAction())) {
                throw new RuntimeException("Invalid action. Use 'stake' for staking.");
            }
            
            MassCoinDTO.WalletInfo wallet = massCoinService.stakeMass(userId, request.getAmount());
            return ResponseEntity.ok(wallet);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @PostMapping("/unstake")
    public ResponseEntity<?> unstakeMass(
            Authentication authentication,
            @RequestBody MassCoinDTO.StakingRequest request) {
        try {
            Long userId = Long.valueOf(authentication.getName());
            
            if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
                throw new RuntimeException("Valid amount is required");
            }
            
            if (!"unstake".equals(request.getAction())) {
                throw new RuntimeException("Invalid action. Use 'unstake' for unstaking.");
            }
            
            MassCoinDTO.WalletInfo wallet = massCoinService.unstakeMass(userId, request.getAmount());
            return ResponseEntity.ok(wallet);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    // Query Endpoints
    
    @GetMapping("/transactions")
    public ResponseEntity<?> getUserTransactions(
            Authentication authentication,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        try {
            Long userId = Long.valueOf(authentication.getName());
            List<MassCoinDTO.TransactionInfo> transactions = massCoinService.getUserTransactions(userId, page, size);
            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @GetMapping("/stats/user")
    public ResponseEntity<?> getUserStats(Authentication authentication) {
        try {
            Long userId = Long.valueOf(authentication.getName());
            MassCoinDTO.UserStats stats = massCoinService.getUserStats(userId);
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @GetMapping("/stats/platform")
    public ResponseEntity<?> getPlatformStats() {
        try {
            MassCoinDTO.PlatformStats stats = massCoinService.getPlatformStats();
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    // Health Check
    @GetMapping("/health")
    public ResponseEntity<?> healthCheck() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "Mass Coin service is running");
        response.put("version", "1.0.0");
        return ResponseEntity.ok(response);
    }
} 