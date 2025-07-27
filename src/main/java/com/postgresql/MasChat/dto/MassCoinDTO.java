package com.postgresql.MasChat.dto;

import com.postgresql.MasChat.model.MassCoinTransaction;
import com.postgresql.MasChat.model.UserWallet;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MassCoinDTO {
    
    // Wallet DTOs
    public static class WalletInfo {
        private Long userId;
        private String walletAddress;
        private BigDecimal balance;
        private BigDecimal stakedAmount;
        private BigDecimal totalEarned;
        private BigDecimal totalSpent;
        private UserWallet.WalletType walletType;
        private Boolean isActive;
        private LocalDateTime lastSyncAt;
        
        public WalletInfo() {}
        
        public WalletInfo(UserWallet wallet) {
            this.userId = wallet.getUser().getId();
            this.walletAddress = wallet.getWalletAddress();
            this.balance = wallet.getBalance();
            this.stakedAmount = wallet.getStakedAmount();
            this.totalEarned = wallet.getTotalEarned();
            this.totalSpent = wallet.getTotalSpent();
            this.walletType = wallet.getWalletType();
            this.isActive = wallet.getIsActive();
            this.lastSyncAt = wallet.getLastSyncAt();
        }
        
        // Getters and Setters
        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }
        
        public String getWalletAddress() { return walletAddress; }
        public void setWalletAddress(String walletAddress) { this.walletAddress = walletAddress; }
        
        public BigDecimal getBalance() { return balance; }
        public void setBalance(BigDecimal balance) { this.balance = balance; }
        
        public BigDecimal getStakedAmount() { return stakedAmount; }
        public void setStakedAmount(BigDecimal stakedAmount) { this.stakedAmount = stakedAmount; }
        
        public BigDecimal getTotalEarned() { return totalEarned; }
        public void setTotalEarned(BigDecimal totalEarned) { this.totalEarned = totalEarned; }
        
        public BigDecimal getTotalSpent() { return totalSpent; }
        public void setTotalSpent(BigDecimal totalSpent) { this.totalSpent = totalSpent; }
        
        public UserWallet.WalletType getWalletType() { return walletType; }
        public void setWalletType(UserWallet.WalletType walletType) { this.walletType = walletType; }
        
        public Boolean getIsActive() { return isActive; }
        public void setIsActive(Boolean isActive) { this.isActive = isActive; }
        
        public LocalDateTime getLastSyncAt() { return lastSyncAt; }
        public void setLastSyncAt(LocalDateTime lastSyncAt) { this.lastSyncAt = lastSyncAt; }
    }
    
    // Transaction DTOs
    public static class TransactionInfo {
        private Long id;
        private Long senderId;
        private String senderName;
        private String senderUsername;
        private Long recipientId;
        private String recipientName;
        private String recipientUsername;
        private BigDecimal amount;
        private String transactionHash;
        private MassCoinTransaction.TransactionType transactionType;
        private MassCoinTransaction.TransactionStatus status;
        private BigDecimal gasFee;
        private BigDecimal usdValue;
        private LocalDateTime createdAt;
        private String description;
        private Long blockNumber;
        
        public TransactionInfo() {}
        
        public TransactionInfo(MassCoinTransaction transaction) {
            this.id = transaction.getId();
            this.senderId = transaction.getSender().getId();
            this.senderName = transaction.getSender().getFullName();
            this.senderUsername = transaction.getSender().getUsername();
            this.recipientId = transaction.getRecipient().getId();
            this.recipientName = transaction.getRecipient().getFullName();
            this.recipientUsername = transaction.getRecipient().getUsername();
            this.amount = transaction.getAmount();
            this.transactionHash = transaction.getTransactionHash();
            this.transactionType = transaction.getTransactionType();
            this.status = transaction.getStatus();
            this.gasFee = transaction.getGasFee();
            this.usdValue = transaction.getUsdValue();
            this.createdAt = transaction.getCreatedAt();
            this.description = transaction.getDescription();
            this.blockNumber = transaction.getBlockNumber();
        }
        
        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        
        public Long getSenderId() { return senderId; }
        public void setSenderId(Long senderId) { this.senderId = senderId; }
        
        public String getSenderName() { return senderName; }
        public void setSenderName(String senderName) { this.senderName = senderName; }
        
        public String getSenderUsername() { return senderUsername; }
        public void setSenderUsername(String senderUsername) { this.senderUsername = senderUsername; }
        
        public Long getRecipientId() { return recipientId; }
        public void setRecipientId(Long recipientId) { this.recipientId = recipientId; }
        
        public String getRecipientName() { return recipientName; }
        public void setRecipientName(String recipientName) { this.recipientName = recipientName; }
        
        public String getRecipientUsername() { return recipientUsername; }
        public void setRecipientUsername(String recipientUsername) { this.recipientUsername = recipientUsername; }
        
        public BigDecimal getAmount() { return amount; }
        public void setAmount(BigDecimal amount) { this.amount = amount; }
        
        public String getTransactionHash() { return transactionHash; }
        public void setTransactionHash(String transactionHash) { this.transactionHash = transactionHash; }
        
        public MassCoinTransaction.TransactionType getTransactionType() { return transactionType; }
        public void setTransactionType(MassCoinTransaction.TransactionType transactionType) { this.transactionType = transactionType; }
        
        public MassCoinTransaction.TransactionStatus getStatus() { return status; }
        public void setStatus(MassCoinTransaction.TransactionStatus status) { this.status = status; }
        
        public BigDecimal getGasFee() { return gasFee; }
        public void setGasFee(BigDecimal gasFee) { this.gasFee = gasFee; }
        
        public BigDecimal getUsdValue() { return usdValue; }
        public void setUsdValue(BigDecimal usdValue) { this.usdValue = usdValue; }
        
        public LocalDateTime getCreatedAt() { return createdAt; }
        public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        
        public Long getBlockNumber() { return blockNumber; }
        public void setBlockNumber(Long blockNumber) { this.blockNumber = blockNumber; }
    }
    
    // Transfer Request DTO
    public static class TransferRequest {
        private String recipientId;
        private BigDecimal amount;
        private String description;
        private MassCoinTransaction.TransactionType transactionType;
        
        public TransferRequest() {}
        
        // Getters and Setters
        public String getRecipientId() { return recipientId; }
        public void setRecipientId(String recipientId) { this.recipientId = recipientId; }
        
        public BigDecimal getAmount() { return amount; }
        public void setAmount(BigDecimal amount) { this.amount = amount; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        
        public MassCoinTransaction.TransactionType getTransactionType() { return transactionType; }
        public void setTransactionType(MassCoinTransaction.TransactionType transactionType) { this.transactionType = transactionType; }
    }
    
    // Staking DTOs
    public static class StakingRequest {
        private BigDecimal amount;
        private String action; // "stake" or "unstake"
        
        public StakingRequest() {}
        
        // Getters and Setters
        public BigDecimal getAmount() { return amount; }
        public void setAmount(BigDecimal amount) { this.amount = amount; }
        
        public String getAction() { return action; }
        public void setAction(String action) { this.action = action; }
    }
    
    // Platform Statistics DTO
    public static class PlatformStats {
        private BigDecimal totalBalance;
        private BigDecimal totalStaked;
        private BigDecimal totalVolume;
        private Long totalWallets;
        private Long totalTransactions;
        private BigDecimal massPrice;
        
        public PlatformStats() {}
        
        // Getters and Setters
        public BigDecimal getTotalBalance() { return totalBalance; }
        public void setTotalBalance(BigDecimal totalBalance) { this.totalBalance = totalBalance; }
        
        public BigDecimal getTotalStaked() { return totalStaked; }
        public void setTotalStaked(BigDecimal totalStaked) { this.totalStaked = totalStaked; }
        
        public BigDecimal getTotalVolume() { return totalVolume; }
        public void setTotalVolume(BigDecimal totalVolume) { this.totalVolume = totalVolume; }
        
        public Long getTotalWallets() { return totalWallets; }
        public void setTotalWallets(Long totalWallets) { this.totalWallets = totalWallets; }
        
        public Long getTotalTransactions() { return totalTransactions; }
        public void setTotalTransactions(Long totalTransactions) { this.totalTransactions = totalTransactions; }
        
        public BigDecimal getMassPrice() { return massPrice; }
        public void setMassPrice(BigDecimal massPrice) { this.massPrice = massPrice; }
    }
    
    // User Statistics DTO
    public static class UserStats {
        private BigDecimal balance;
        private BigDecimal stakedAmount;
        private BigDecimal totalEarned;
        private BigDecimal totalSpent;
        private Long transactionCount;
        private BigDecimal totalSent;
        private BigDecimal totalReceived;
        
        public UserStats() {}
        
        // Getters and Setters
        public BigDecimal getBalance() { return balance; }
        public void setBalance(BigDecimal balance) { this.balance = balance; }
        
        public BigDecimal getStakedAmount() { return stakedAmount; }
        public void setStakedAmount(BigDecimal stakedAmount) { this.stakedAmount = stakedAmount; }
        
        public BigDecimal getTotalEarned() { return totalEarned; }
        public void setTotalEarned(BigDecimal totalEarned) { this.totalEarned = totalEarned; }
        
        public BigDecimal getTotalSpent() { return totalSpent; }
        public void setTotalSpent(BigDecimal totalSpent) { this.totalSpent = totalSpent; }
        
        public Long getTransactionCount() { return transactionCount; }
        public void setTransactionCount(Long transactionCount) { this.transactionCount = transactionCount; }
        
        public BigDecimal getTotalSent() { return totalSent; }
        public void setTotalSent(BigDecimal totalSent) { this.totalSent = totalSent; }
        
        public BigDecimal getTotalReceived() { return totalReceived; }
        public void setTotalReceived(BigDecimal totalReceived) { this.totalReceived = totalReceived; }
    }
} 