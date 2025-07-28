-- Migration V9: Add Mass Coin Transfer Requests and Enhanced Notifications

-- Create mass_coin_transfer_requests table
CREATE TABLE mass_coin_transfer_requests (
    id BIGSERIAL PRIMARY KEY,
    sender_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    recipient_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    amount DECIMAL(18,6) NOT NULL,
    message TEXT,
    context_type VARCHAR(50) NOT NULL,
    context_id VARCHAR(255),
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    expires_at TIMESTAMP NOT NULL,
    
    CONSTRAINT chk_context_type CHECK (context_type IN ('POST', 'REEL', 'CHAT', 'DIRECT', 'MASS_COIN_SECTION')),
    CONSTRAINT chk_status CHECK (status IN ('PENDING', 'APPROVED', 'REJECTED', 'EXPIRED', 'CANCELLED')),
    CONSTRAINT chk_amount_positive CHECK (amount > 0)
);

-- Create indexes for mass_coin_transfer_requests
CREATE INDEX idx_transfer_requests_recipient_status ON mass_coin_transfer_requests(recipient_id, status);
CREATE INDEX idx_transfer_requests_sender ON mass_coin_transfer_requests(sender_id);
CREATE INDEX idx_transfer_requests_context ON mass_coin_transfer_requests(context_type, context_id);
CREATE INDEX idx_transfer_requests_expires ON mass_coin_transfer_requests(expires_at) WHERE status = 'PENDING';

-- Enhance notifications table
ALTER TABLE notifications 
ADD COLUMN title VARCHAR(255) NOT NULL DEFAULT 'Notification',
ADD COLUMN notification_type VARCHAR(50) NOT NULL DEFAULT 'SYSTEM_MESSAGE',
ADD COLUMN related_id VARCHAR(255),
ADD COLUMN related_type VARCHAR(50),
ADD COLUMN sender_id BIGINT REFERENCES users(id) ON DELETE SET NULL,
ADD COLUMN sender_name VARCHAR(255),
ADD COLUMN sender_avatar TEXT,
ADD COLUMN deleted BOOLEAN NOT NULL DEFAULT FALSE,
ADD COLUMN read_at TIMESTAMP,
ADD COLUMN deleted_at TIMESTAMP;

-- Add constraints for notifications
ALTER TABLE notifications 
ADD CONSTRAINT chk_notification_type CHECK (notification_type IN (
    'MESSAGE', 'FRIEND_REQUEST', 'POST_LIKE', 'POST_COMMENT', 'REEL_LIKE', 'REEL_COMMENT',
    'MASS_COIN_TRANSFER_REQUEST', 'MASS_COIN_TRANSFER_APPROVED', 'MASS_COIN_TRANSFER_REJECTED',
    'MASS_COIN_RECEIVED', 'MASS_COIN_SENT', 'SYSTEM_MESSAGE'
));

-- Create indexes for enhanced notifications
CREATE INDEX idx_notifications_user_deleted ON notifications(user_id, deleted) WHERE deleted = FALSE;
CREATE INDEX idx_notifications_user_read ON notifications(user_id, read) WHERE deleted = FALSE;
CREATE INDEX idx_notifications_type ON notifications(notification_type) WHERE deleted = FALSE;
CREATE INDEX idx_notifications_related ON notifications(related_type, related_id) WHERE deleted = FALSE;
CREATE INDEX idx_notifications_sender ON notifications(sender_id) WHERE deleted = FALSE;
CREATE INDEX idx_notifications_masscoin ON notifications(user_id, notification_type) 
WHERE deleted = FALSE AND notification_type LIKE 'MASS_COIN%';

-- Update existing notifications to have default values
UPDATE notifications SET 
    title = 'Notification',
    notification_type = 'SYSTEM_MESSAGE',
    deleted = FALSE
WHERE title IS NULL OR notification_type IS NULL;

-- Ensure all users have wallets with 1000 initial tokens
INSERT INTO user_wallets (user_id, wallet_address, balance, staked_amount, total_earned, total_spent, wallet_type, is_active, created_at, updated_at)
SELECT 
    u.id,
    'MC' || UPPER(SUBSTRING(MD5(RANDOM()::TEXT) FROM 1 FOR 32)),
    1000.0,
    0.0,
    1000.0,
    0.0,
    'CUSTODIAL',
    TRUE,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
FROM users u
WHERE NOT EXISTS (
    SELECT 1 FROM user_wallets w WHERE w.user_id = u.id
);

-- Create initial transactions for the 1000 token bonus
INSERT INTO mass_coin_transactions (sender_id, recipient_id, amount, transaction_type, status, description, created_at, updated_at)
SELECT 
    NULL,
    u.id,
    1000.0,
    'AIRDROP',
    'CONFIRMED',
    'Welcome bonus - 1000 Mass Coins',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
FROM users u
WHERE NOT EXISTS (
    SELECT 1 FROM mass_coin_transactions t 
    WHERE t.recipient_id = u.id 
    AND t.transaction_type = 'AIRDROP'
    AND t.description = 'Welcome bonus - 1000 Mass Coins'
); 