package com.postgresql.MasChat.service;

import com.postgresql.MasChat.dto.MassCoinDTO;
import com.postgresql.MasChat.model.MassCoinTransaction;
import com.postgresql.MasChat.model.User;
import com.postgresql.MasChat.model.UserWallet;
import com.postgresql.MasChat.repository.MassCoinTransactionRepository;
import com.postgresql.MasChat.repository.UserRepository;
import com.postgresql.MasChat.repository.UserWalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MassCoinService {
    
    @Autowired
    private UserWalletRepository userWalletRepository;
    
    @Autowired
    private MassCoinTransactionRepository transactionRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    // Wallet Operations
    
    @Transactional
    public MassCoinDTO.WalletInfo createWallet(User user) {
        // Check if wallet already exists
        if (userWalletRepository.existsByUser(user)) {
            throw new RuntimeException("Wallet already exists for user: " + user.getId());
        }
        
        // Generate wallet address (in production, this would be a real blockchain address)
        String walletAddress = generateWalletAddress();
        
        UserWallet wallet = new UserWallet(user, walletAddress, UserWallet.WalletType.CUSTODIAL);
        wallet = userWalletRepository.save(wallet);
        
        return new MassCoinDTO.WalletInfo(wallet);
    }
    
    @Transactional
    public MassCoinDTO.WalletInfo getWallet(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found: " + userId);
        }
        
        Optional<UserWallet> walletOpt = userWalletRepository.findByUser(userOpt.get());
        if (walletOpt.isEmpty()) {
            // Create wallet if it doesn't exist
            return createWallet(userOpt.get());
        }
        
        return new MassCoinDTO.WalletInfo(walletOpt.get());
    }
    
    @Transactional
    public MassCoinDTO.WalletInfo updateWalletAddress(Long userId, String newAddress) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found: " + userId);
        }
        
        Optional<UserWallet> walletOpt = userWalletRepository.findByUser(userOpt.get());
        if (walletOpt.isEmpty()) {
            throw new RuntimeException("Wallet not found for user: " + userId);
        }
        
        UserWallet wallet = walletOpt.get();
        wallet.setWalletAddress(newAddress);
        wallet.setWalletType(UserWallet.WalletType.EXTERNAL);
        wallet = userWalletRepository.save(wallet);
        
        return new MassCoinDTO.WalletInfo(wallet);
    }
    
    // Transaction Operations
    
    @Transactional
    public MassCoinDTO.TransactionInfo transferMass(Long senderId, MassCoinDTO.TransferRequest request) {
        // Validate sender
        Optional<User> senderOpt = userRepository.findById(senderId);
        if (senderOpt.isEmpty()) {
            throw new RuntimeException("Sender not found: " + senderId);
        }
        
        // Validate recipient
        Optional<User> recipientOpt = userRepository.findById(Long.valueOf(request.getRecipientId()));
        if (recipientOpt.isEmpty()) {
            throw new RuntimeException("Recipient not found: " + request.getRecipientId());
        }
        
        User sender = senderOpt.get();
        User recipient = recipientOpt.get();
        
        // Get sender wallet
        Optional<UserWallet> senderWalletOpt = userWalletRepository.findByUser(sender);
        if (senderWalletOpt.isEmpty()) {
            throw new RuntimeException("Sender wallet not found");
        }
        
        // Get or create recipient wallet
        Optional<UserWallet> recipientWalletOpt = userWalletRepository.findByUser(recipient);
        UserWallet recipientWallet;
        if (recipientWalletOpt.isEmpty()) {
            recipientWallet = new UserWallet(recipient, generateWalletAddress(), UserWallet.WalletType.CUSTODIAL);
            recipientWallet = userWalletRepository.save(recipientWallet);
        } else {
            recipientWallet = recipientWalletOpt.get();
        }
        
        UserWallet senderWallet = senderWalletOpt.get();
        
        // Validate balance
        if (senderWallet.getBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient balance");
        }
        
        // Create transaction
        MassCoinTransaction transaction = new MassCoinTransaction(
            sender, 
            recipient, 
            request.getAmount(), 
            request.getTransactionType() != null ? request.getTransactionType() : MassCoinTransaction.TransactionType.P2P_TRANSFER
        );
        transaction.setDescription(request.getDescription());
        transaction.setTransactionHash(generateTransactionHash());
        transaction.setStatus(MassCoinTransaction.TransactionStatus.CONFIRMED);
        transaction.setUsdValue(calculateUsdValue(request.getAmount()));
        
        transaction = transactionRepository.save(transaction);
        
        // Update balances
        senderWallet.subtractBalance(request.getAmount());
        recipientWallet.addBalance(request.getAmount());
        
        userWalletRepository.save(senderWallet);
        userWalletRepository.save(recipientWallet);
        
        return new MassCoinDTO.TransactionInfo(transaction);
    }
    
    @Transactional
    public MassCoinDTO.TransactionInfo tipCreator(Long senderId, String postId, BigDecimal amount, String description) {
        // For now, we'll use a system user as recipient for tips
        // In a real implementation, you'd get the post creator
        Optional<User> systemUser = userRepository.findById(1L); // Assuming system user has ID 1
        if (systemUser.isEmpty()) {
            throw new RuntimeException("System user not found");
        }
        
        MassCoinDTO.TransferRequest request = new MassCoinDTO.TransferRequest();
        request.setRecipientId("1"); // System user ID as string
        request.setAmount(amount);
        request.setDescription(description != null ? description : "Tip for post: " + postId);
        request.setTransactionType(MassCoinTransaction.TransactionType.CONTENT_TIP);
        
        return transferMass(senderId, request);
    }
    
    @Transactional
    public MassCoinDTO.TransactionInfo rewardUser(Long userId, BigDecimal amount, String reason) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found: " + userId);
        }
        
        // Get or create user wallet
        Optional<UserWallet> walletOpt = userWalletRepository.findByUser(userOpt.get());
        UserWallet wallet;
        if (walletOpt.isEmpty()) {
            wallet = new UserWallet(userOpt.get(), generateWalletAddress(), UserWallet.WalletType.CUSTODIAL);
            wallet = userWalletRepository.save(wallet);
        } else {
            wallet = walletOpt.get();
        }
        
        // Create reward transaction (from system to user)
        Optional<User> systemUser = userRepository.findById(1L); // Assuming system user has ID 1
        if (systemUser.isEmpty()) {
            throw new RuntimeException("System user not found");
        }
        
        MassCoinTransaction transaction = new MassCoinTransaction(
            systemUser.get(),
            userOpt.get(),
            amount,
            MassCoinTransaction.TransactionType.REWARD_DISTRIBUTION
        );
        transaction.setDescription(reason);
        transaction.setTransactionHash(generateTransactionHash());
        transaction.setStatus(MassCoinTransaction.TransactionStatus.CONFIRMED);
        transaction.setUsdValue(calculateUsdValue(amount));
        
        transaction = transactionRepository.save(transaction);
        
        // Update user balance
        wallet.addBalance(amount);
        userWalletRepository.save(wallet);
        
        return new MassCoinDTO.TransactionInfo(transaction);
    }
    
    // Staking Operations
    
    @Transactional
    public MassCoinDTO.WalletInfo stakeMass(Long userId, BigDecimal amount) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found: " + userId);
        }
        
        Optional<UserWallet> walletOpt = userWalletRepository.findByUser(userOpt.get());
        if (walletOpt.isEmpty()) {
            throw new RuntimeException("Wallet not found for user: " + userId);
        }
        
        UserWallet wallet = walletOpt.get();
        
        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance for staking");
        }
        
        wallet.stake(amount);
        wallet = userWalletRepository.save(wallet);
        
        return new MassCoinDTO.WalletInfo(wallet);
    }
    
    @Transactional
    public MassCoinDTO.WalletInfo unstakeMass(Long userId, BigDecimal amount) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found: " + userId);
        }
        
        Optional<UserWallet> walletOpt = userWalletRepository.findByUser(userOpt.get());
        if (walletOpt.isEmpty()) {
            throw new RuntimeException("Wallet not found for user: " + userId);
        }
        
        UserWallet wallet = walletOpt.get();
        
        if (wallet.getStakedAmount().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient staked amount");
        }
        
        wallet.unstake(amount);
        wallet = userWalletRepository.save(wallet);
        
        return new MassCoinDTO.WalletInfo(wallet);
    }
    
    // Query Operations
    
    public List<MassCoinDTO.TransactionInfo> getUserTransactions(Long userId, int page, int size) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found: " + userId);
        }
        
        Pageable pageable = PageRequest.of(page, size);
        Page<MassCoinTransaction> transactions = transactionRepository.findByUser(userOpt.get(), pageable);
        
        return transactions.getContent().stream()
            .map(MassCoinDTO.TransactionInfo::new)
            .collect(Collectors.toList());
    }
    
    public MassCoinDTO.UserStats getUserStats(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found: " + userId);
        }
        
        Optional<UserWallet> walletOpt = userWalletRepository.findByUser(userOpt.get());
        if (walletOpt.isEmpty()) {
            throw new RuntimeException("Wallet not found for user: " + userId);
        }
        
        UserWallet wallet = walletOpt.get();
        Object[] stats = transactionRepository.getUserTransactionStats(userOpt.get());
        
        MassCoinDTO.UserStats userStats = new MassCoinDTO.UserStats();
        userStats.setBalance(wallet.getBalance());
        userStats.setStakedAmount(wallet.getStakedAmount());
        userStats.setTotalEarned(wallet.getTotalEarned());
        userStats.setTotalSpent(wallet.getTotalSpent());
        userStats.setTransactionCount((Long) stats[0] + (Long) stats[1]); // sent + received
        userStats.setTotalSent((BigDecimal) stats[2]);
        userStats.setTotalReceived((BigDecimal) stats[3]);
        
        return userStats;
    }
    
    public MassCoinDTO.PlatformStats getPlatformStats() {
        Object[] walletStats = userWalletRepository.getWalletStatistics();
        BigDecimal totalBalance = userWalletRepository.getTotalPlatformBalance();
        BigDecimal totalStaked = userWalletRepository.getTotalStakedAmount();
        BigDecimal totalVolume = transactionRepository.getTotalPlatformVolume();
        
        MassCoinDTO.PlatformStats platformStats = new MassCoinDTO.PlatformStats();
        platformStats.setTotalBalance(totalBalance);
        platformStats.setTotalStaked(totalStaked);
        platformStats.setTotalVolume(totalVolume);
        platformStats.setTotalWallets((Long) walletStats[0]);
        platformStats.setTotalTransactions(transactionRepository.count());
        platformStats.setMassPrice(new BigDecimal("0.001")); // Mock price, in real app would come from oracle
        
        return platformStats;
    }
    
    // Helper Methods
    
    private String generateWalletAddress() {
        return "0x" + UUID.randomUUID().toString().replace("-", "").substring(0, 40);
    }
    
    private String generateTransactionHash() {
        return "0x" + UUID.randomUUID().toString().replace("-", "").substring(0, 64);
    }
    
    private BigDecimal calculateUsdValue(BigDecimal massAmount) {
        // Mock USD value calculation (in real app would use price oracle)
        BigDecimal massPrice = new BigDecimal("0.001");
        return massAmount.multiply(massPrice);
    }
} 