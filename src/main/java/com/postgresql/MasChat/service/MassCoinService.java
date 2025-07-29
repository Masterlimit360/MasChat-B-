package com.postgresql.MasChat.service;

import com.postgresql.MasChat.dto.MassCoinDTO;
import com.postgresql.MasChat.model.MassCoinTransaction;
import com.postgresql.MasChat.model.MassCoinTransferRequest;
import com.postgresql.MasChat.model.Notification;
import com.postgresql.MasChat.model.User;
import com.postgresql.MasChat.model.UserWallet;
import com.postgresql.MasChat.repository.MassCoinTransactionRepository;
import com.postgresql.MasChat.repository.MassCoinTransferRequestRepository;
import com.postgresql.MasChat.repository.NotificationRepository;
import com.postgresql.MasChat.repository.UserRepository;
import com.postgresql.MasChat.repository.UserWalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MassCoinService {

    @Autowired
    private UserWalletRepository walletRepository;

    @Autowired
    private MassCoinTransactionRepository transactionRepository;

    @Autowired
    private MassCoinTransferRequestRepository transferRequestRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    // Initialize wallet with 1000 tokens for new users
    @Transactional
    public MassCoinDTO.WalletInfo createWallet(User user) {
        Optional<UserWallet> existingWallet = walletRepository.findByUserId(user.getId());
        if (existingWallet.isPresent()) {
            return new MassCoinDTO.WalletInfo(existingWallet.get());
        }

        UserWallet wallet = new UserWallet(user);
        wallet.setBalance(new BigDecimal("1000.0")); // Give 1000 tokens on signup
        wallet.setWalletAddress(generateWalletAddress());
        wallet = walletRepository.save(wallet);

        // Create initial transaction record
        MassCoinTransaction initialTransaction = new MassCoinTransaction(
            null, user, new BigDecimal("1000.0"), MassCoinTransaction.TransactionType.AIRDROP
        );
        initialTransaction.setStatus(MassCoinTransaction.TransactionStatus.CONFIRMED);
        initialTransaction.setDescription("Welcome bonus - 1000 Mass Coins");
        transactionRepository.save(initialTransaction);

        return new MassCoinDTO.WalletInfo(wallet);
    }

    public MassCoinDTO.WalletInfo getWallet(Long userId) {
        Optional<UserWallet> walletOpt = walletRepository.findByUserId(userId);
        if (walletOpt.isPresent()) {
            return new MassCoinDTO.WalletInfo(walletOpt.get());
        }
        
        // Create wallet if it doesn't exist
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return createWallet(user);
    }

    @Transactional
    public MassCoinDTO.WalletInfo updateWalletAddress(Long userId, String newAddress) {
        UserWallet wallet = walletRepository.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("Wallet not found"));
        
        wallet.setWalletAddress(newAddress);
        wallet = walletRepository.save(wallet);
        
        return new MassCoinDTO.WalletInfo(wallet);
    }

    // Create transfer request (requires approval)
    @Transactional
    public MassCoinDTO.TransferRequestInfo createTransferRequest(Long senderId, MassCoinDTO.TransferRequest request) {
        User sender = userRepository.findById(senderId).orElseThrow(() -> new RuntimeException("Sender not found"));
        User recipient = userRepository.findById(request.getRecipientId()).orElseThrow(() -> new RuntimeException("Recipient not found"));
        
        // Check if sender has enough balance
        UserWallet senderWallet = walletRepository.findByUserId(senderId)
            .orElseThrow(() -> new RuntimeException("Sender wallet not found"));
        
        if (senderWallet.getBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient balance");
        }
        
        // Check if request already exists
        Optional<MassCoinTransferRequest> existingRequest = transferRequestRepository
            .findBySenderIdAndRecipientIdAndContextTypeAndContextIdAndStatus(
                senderId, 
                request.getRecipientId(), 
                request.getContextType(), 
                request.getContextId(), 
                MassCoinTransferRequest.RequestStatus.PENDING
            );
        
        if (existingRequest.isPresent()) {
            throw new RuntimeException("Transfer request already exists");
        }
        
        // Create transfer request
        MassCoinTransferRequest transferRequest = new MassCoinTransferRequest(
            sender, 
            recipient, 
            request.getAmount(), 
            request.getMessage(), 
            request.getContextType(), 
            request.getContextId()
        );
        
        transferRequest = transferRequestRepository.save(transferRequest);
        
        // Deduct amount from sender's wallet (temporarily)
        senderWallet.subtractBalance(request.getAmount());
        walletRepository.save(senderWallet);
        
        // Create notification for recipient
        createNotification(
            recipient,
            "Mass Coin Transfer Request",
            sender.getFullName() + " wants to send you " + request.getAmount() + " Mass Coins",
            Notification.NotificationType.MASS_COIN_TRANSFER_REQUEST,
            transferRequest.getId().toString(),
            "MASS_COIN_TRANSFER",
            senderId,
            sender.getFullName(),
            sender.getProfilePicture()
        );
        
        return new MassCoinDTO.TransferRequestInfo(transferRequest);
    }

    // Approve transfer request
    @Transactional
    public MassCoinDTO.TransactionInfo approveTransferRequest(Long recipientId, Long requestId) {
        MassCoinTransferRequest request = transferRequestRepository.findById(requestId)
            .orElseThrow(() -> new RuntimeException("Transfer request not found"));
        
        if (!request.getRecipient().getId().equals(recipientId)) {
            throw new RuntimeException("Unauthorized to approve this request");
        }
        
        if (request.getStatus() != MassCoinTransferRequest.RequestStatus.PENDING) {
            throw new RuntimeException("Request is not pending");
        }
        
        if (request.isExpired()) {
            request.setStatus(MassCoinTransferRequest.RequestStatus.EXPIRED);
            transferRequestRepository.save(request);
            throw new RuntimeException("Request has expired");
        }
        
        // Update request status
        request.setStatus(MassCoinTransferRequest.RequestStatus.APPROVED);
        transferRequestRepository.save(request);
        
        // Transfer the coins
        UserWallet recipientWallet = walletRepository.findByUserId(recipientId)
            .orElseGet(() -> {
                createWallet(userRepository.findById(recipientId).get());
                return walletRepository.findByUserId(recipientId).orElseThrow(() -> new RuntimeException("Wallet creation failed"));
            });
        
        recipientWallet.addBalance(request.getAmount());
        walletRepository.save(recipientWallet);
        
        // Create transaction record
        MassCoinTransaction transaction = new MassCoinTransaction(
            request.getSender(), 
            request.getRecipient(), 
            request.getAmount(), 
            MassCoinTransaction.TransactionType.P2P_TRANSFER
        );
        transaction.setStatus(MassCoinTransaction.TransactionStatus.CONFIRMED);
        transaction.setDescription(request.getMessage());
        transaction = transactionRepository.save(transaction);
        
        // Create notifications
        createNotification(
            request.getRecipient(),
            "Mass Coin Received",
            "You received " + request.getAmount() + " Mass Coins from " + request.getSender().getFullName(),
            Notification.NotificationType.MASS_COIN_RECEIVED,
            transaction.getId().toString(),
            "MASS_COIN_TRANSACTION",
            request.getSender().getId(),
            request.getSender().getFullName(),
            request.getSender().getProfilePicture()
        );
        
        createNotification(
            request.getSender(),
            "Transfer Approved",
            request.getRecipient().getFullName() + " approved your transfer of " + request.getAmount() + " Mass Coins",
            Notification.NotificationType.MASS_COIN_TRANSFER_APPROVED,
            transaction.getId().toString(),
            "MASS_COIN_TRANSACTION",
            request.getRecipient().getId(),
            request.getRecipient().getFullName(),
            request.getRecipient().getProfilePicture()
        );
        
        return new MassCoinDTO.TransactionInfo(transaction);
    }

    // Reject transfer request
    @Transactional
    public void rejectTransferRequest(Long recipientId, Long requestId) {
        MassCoinTransferRequest request = transferRequestRepository.findById(requestId)
            .orElseThrow(() -> new RuntimeException("Transfer request not found"));
        
        if (!request.getRecipient().getId().equals(recipientId)) {
            throw new RuntimeException("Unauthorized to reject this request");
        }
        
        if (request.getStatus() != MassCoinTransferRequest.RequestStatus.PENDING) {
            throw new RuntimeException("Request is not pending");
        }
        
        // Update request status
        request.setStatus(MassCoinTransferRequest.RequestStatus.REJECTED);
        transferRequestRepository.save(request);
        
        // Refund sender's wallet
        UserWallet senderWallet = walletRepository.findByUserId(request.getSender().getId())
            .orElseThrow(() -> new RuntimeException("Sender wallet not found"));
        
        senderWallet.addBalance(request.getAmount());
        walletRepository.save(senderWallet);
        
        // Create notification for sender
        createNotification(
            request.getSender(),
            "Transfer Rejected",
            request.getRecipient().getFullName() + " rejected your transfer of " + request.getAmount() + " Mass Coins. Amount has been refunded.",
            Notification.NotificationType.MASS_COIN_TRANSFER_REJECTED,
            requestId.toString(),
            "MASS_COIN_TRANSFER",
            request.getRecipient().getId(),
            request.getRecipient().getFullName(),
            request.getRecipient().getProfilePicture()
        );
    }

    // Direct transfer (no approval needed)
    @Transactional
    public MassCoinDTO.TransactionInfo transferMass(Long senderId, MassCoinDTO.TransferRequest request) {
        User sender = userRepository.findById(senderId).orElseThrow(() -> new RuntimeException("Sender not found"));
        User recipient = userRepository.findById(request.getRecipientId()).orElseThrow(() -> new RuntimeException("Recipient not found"));
        
        UserWallet senderWallet = walletRepository.findByUserId(senderId)
            .orElseThrow(() -> new RuntimeException("Sender wallet not found"));
        
        if (senderWallet.getBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient balance");
        }
        
        // Deduct from sender
        senderWallet.subtractBalance(request.getAmount());
        walletRepository.save(senderWallet);
        
        // Add to recipient
        UserWallet recipientWallet = walletRepository.findByUserId(request.getRecipientId())
            .orElseGet(() -> {
                createWallet(recipient);
                return walletRepository.findByUserId(request.getRecipientId()).orElseThrow(() -> new RuntimeException("Wallet creation failed"));
            });
        
        recipientWallet.addBalance(request.getAmount());
        walletRepository.save(recipientWallet);
        
        // Create transaction
        MassCoinTransaction transaction = new MassCoinTransaction(
            sender, 
            recipient, 
            request.getAmount(), 
            request.getTransactionType() != null ? request.getTransactionType() : MassCoinTransaction.TransactionType.P2P_TRANSFER
        );
        transaction.setStatus(MassCoinTransaction.TransactionStatus.CONFIRMED);
        transaction.setDescription(request.getMessage());
        transaction = transactionRepository.save(transaction);
        
        // Create notifications
        createNotification(
            recipient,
            "Mass Coin Received",
            "You received " + request.getAmount() + " Mass Coins from " + sender.getFullName(),
            Notification.NotificationType.MASS_COIN_RECEIVED,
            transaction.getId().toString(),
            "MASS_COIN_TRANSACTION",
            senderId,
            sender.getFullName(),
            sender.getProfilePicture()
        );
        
        createNotification(
            sender,
            "Mass Coin Sent",
            "You sent " + request.getAmount() + " Mass Coins to " + recipient.getFullName(),
            Notification.NotificationType.MASS_COIN_SENT,
            transaction.getId().toString(),
            "MASS_COIN_TRANSACTION",
            request.getRecipientId(),
            recipient.getFullName(),
            recipient.getProfilePicture()
        );
        
        return new MassCoinDTO.TransactionInfo(transaction);
    }

    // Get transfer requests for a user
    public List<MassCoinDTO.TransferRequestInfo> getTransferRequests(Long userId) {
        List<MassCoinTransferRequest> requests = transferRequestRepository.findByRecipientIdAndStatusOrderByCreatedAtDesc(
            userId, MassCoinTransferRequest.RequestStatus.PENDING
        );
        return requests.stream().map(MassCoinDTO.TransferRequestInfo::new).toList();
    }

    // Get pending transfer requests count
    public long getPendingTransferRequestsCount(Long userId) {
        return transferRequestRepository.countByRecipientIdAndStatus(userId, MassCoinTransferRequest.RequestStatus.PENDING);
    }

    // Get transactions for a user
    public Page<MassCoinDTO.TransactionInfo> getUserTransactions(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<MassCoinTransaction> transactions = transactionRepository.findBySenderIdOrRecipientIdOrderByCreatedAtDesc(userId, pageable);
        return transactions.map(MassCoinDTO.TransactionInfo::new);
    }

    // Get user statistics
    public MassCoinDTO.UserStats getUserStats(Long userId) {
        MassCoinDTO.UserStats stats = new MassCoinDTO.UserStats();
        
        // Get total transactions
        long totalTransactions = transactionRepository.countBySenderIdOrRecipientId(userId);
        stats.setTotalTransactions(totalTransactions);
        
        // Get total volume
        BigDecimal totalVolume = transactionRepository.getTotalVolumeByUserId(userId);
        stats.setTotalVolume(totalVolume != null ? totalVolume : BigDecimal.ZERO);
        
        // Get average transaction amount
        BigDecimal avgAmount = transactionRepository.getAverageTransactionAmountByUserId(userId);
        stats.setAverageTransactionAmount(avgAmount != null ? avgAmount : BigDecimal.ZERO);
        
        // Get tips received
        long tipsReceived = transactionRepository.countByRecipientIdAndTransactionType(userId, MassCoinTransaction.TransactionType.CONTENT_TIP);
        stats.setTotalTipsReceived(tipsReceived);
        
        // Get total tips amount received
        BigDecimal tipsAmount = transactionRepository.getTotalTipsAmountReceivedByUserId(userId);
        stats.setTotalTipsAmount(tipsAmount != null ? tipsAmount : BigDecimal.ZERO);
        
        // Get tips sent
        long tipsSent = transactionRepository.countBySenderIdAndTransactionType(userId, MassCoinTransaction.TransactionType.CONTENT_TIP);
        stats.setTotalTipsSent(tipsSent);
        
        // Get total tips amount sent
        BigDecimal tipsSentAmount = transactionRepository.getTotalTipsAmountSentByUserId(userId);
        stats.setTotalTipsSentAmount(tipsSentAmount != null ? tipsSentAmount : BigDecimal.ZERO);
        
        return stats;
    }

    // Search users by ID, username, or fullname
    public List<MassCoinDTO.UserSearchResult> searchUsers(String query, Long currentUserId) {
        if (query == null || query.trim().isEmpty()) {
            return List.of();
        }
        
        String searchQuery = query.trim().toLowerCase();
        
        // Try to find by ID first
        try {
            Long userId = Long.parseLong(searchQuery);
            Optional<User> userById = userRepository.findById(userId);
            if (userById.isPresent() && !userById.get().getId().equals(currentUserId)) {
                User user = userById.get();
                return List.of(new MassCoinDTO.UserSearchResult(
                    user.getId(),
                    user.getUsername(),
                    user.getFullName(),
                    user.getProfilePicture(),
                    user.getEmail()
                ));
            }
        } catch (NumberFormatException e) {
            // Not a valid ID, continue with username/fullname search
        }
        
        // Search by username or fullname
        List<User> users = userRepository.findByUsernameContainingIgnoreCaseOrFullNameContainingIgnoreCase(searchQuery, searchQuery);
        
        return users.stream()
            .filter(user -> !user.getId().equals(currentUserId)) // Exclude current user
            .map(user -> new MassCoinDTO.UserSearchResult(
                user.getId(),
                user.getUsername(),
                user.getFullName(),
                user.getProfilePicture(),
                user.getEmail()
            ))
            .limit(10) // Limit results
            .toList();
    }

    // Tip creator (direct transfer)
    @Transactional
    public MassCoinDTO.TransactionInfo tipCreator(Long senderId, String postId, BigDecimal amount, String description) {
        // Find post creator
        // This would need to be implemented based on your post structure
        // For now, we'll use a placeholder
        User recipient = userRepository.findById(1L).orElseThrow(() -> new RuntimeException("Recipient not found"));
        
        MassCoinDTO.TransferRequest request = new MassCoinDTO.TransferRequest();
        request.setRecipientId(recipient.getId());
        request.setAmount(amount);
        request.setMessage(description);
        request.setTransactionType(MassCoinTransaction.TransactionType.CONTENT_TIP);
        request.setContextType(MassCoinTransferRequest.ContextType.POST);
        request.setContextId(postId);
        
        return transferMass(senderId, request);
    }

    // Reward user (system reward)
    @Transactional
    public MassCoinDTO.TransactionInfo rewardUser(Long userId, BigDecimal amount, String reason) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        
        UserWallet wallet = walletRepository.findByUserId(userId)
            .orElseGet(() -> {
                createWallet(user);
                return walletRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Wallet creation failed"));
            });
        
        wallet.addBalance(amount);
        walletRepository.save(wallet);
        
        MassCoinTransaction transaction = new MassCoinTransaction(
            null, 
            user, 
            amount, 
            MassCoinTransaction.TransactionType.REWARD_DISTRIBUTION
        );
        transaction.setStatus(MassCoinTransaction.TransactionStatus.CONFIRMED);
        transaction.setDescription(reason);
        transaction = transactionRepository.save(transaction);
        
        // Create notification
        createNotification(
            user,
            "Mass Coin Reward",
            "You received " + amount + " Mass Coins as a reward: " + reason,
            Notification.NotificationType.MASS_COIN_RECEIVED,
            transaction.getId().toString(),
            "MASS_COIN_TRANSACTION",
            null,
            "System",
            null
        );
        
        return new MassCoinDTO.TransactionInfo(transaction);
    }

    // Staking methods
    @Transactional
    public MassCoinDTO.WalletInfo stakeMass(Long userId, BigDecimal amount) {
        UserWallet wallet = walletRepository.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("Wallet not found"));
        
        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance for staking");
        }
        
        wallet.stake(amount);
        wallet = walletRepository.save(wallet);
        
        return new MassCoinDTO.WalletInfo(wallet);
    }

    @Transactional
    public MassCoinDTO.WalletInfo unstakeMass(Long userId, BigDecimal amount) {
        UserWallet wallet = walletRepository.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("Wallet not found"));
        
        if (wallet.getStakedAmount().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient staked amount");
        }
        
        wallet.unstake(amount);
        wallet = walletRepository.save(wallet);
        
        return new MassCoinDTO.WalletInfo(wallet);
    }

    // Scheduled task to expire old requests
    @Scheduled(fixedRate = 3600000) // Run every hour
    @Transactional
    public void expireOldRequests() {
        List<MassCoinTransferRequest> expiredRequests = transferRequestRepository.findRequestsToExpire(LocalDateTime.now());
        
        for (MassCoinTransferRequest request : expiredRequests) {
            request.setStatus(MassCoinTransferRequest.RequestStatus.EXPIRED);
            transferRequestRepository.save(request);
            
            // Refund sender
            UserWallet senderWallet = walletRepository.findByUserId(request.getSender().getId())
                .orElseThrow(() -> new RuntimeException("Sender wallet not found"));
            
            senderWallet.addBalance(request.getAmount());
            walletRepository.save(senderWallet);
            
            // Create notification
            createNotification(
                request.getSender(),
                "Transfer Expired",
                "Your transfer request to " + request.getRecipient().getFullName() + " has expired. Amount has been refunded.",
                Notification.NotificationType.MASS_COIN_TRANSFER_REJECTED,
                request.getId().toString(),
                "MASS_COIN_TRANSFER",
                request.getRecipient().getId(),
                request.getRecipient().getFullName(),
                request.getRecipient().getProfilePicture()
            );
        }
    }

    // Helper methods
    private String generateWalletAddress() {
        return "MC" + UUID.randomUUID().toString().replace("-", "").substring(0, 32).toUpperCase();
    }

    private void createNotification(User user, String title, String message, Notification.NotificationType type, 
                                  String relatedId, String relatedType, Long senderId, String senderName, String senderAvatar) {
        Notification notification = new Notification(user, title, message, type, relatedId, relatedType, senderId, senderName, senderAvatar);
        notificationRepository.save(notification);
    }
} 