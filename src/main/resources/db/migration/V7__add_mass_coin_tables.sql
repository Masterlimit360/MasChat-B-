-- Create user_wallets table
CREATE TABLE user_wallets (
    id BIGSERIAL PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL UNIQUE,
    wallet_address VARCHAR(255) UNIQUE,
    balance DECIMAL(18,6) NOT NULL DEFAULT 0,
    staked_amount DECIMAL(18,6) NOT NULL DEFAULT 0,
    total_earned DECIMAL(18,6) NOT NULL DEFAULT 0,
    total_spent DECIMAL(18,6) NOT NULL DEFAULT 0,
    wallet_type VARCHAR(20) NOT NULL DEFAULT 'CUSTODIAL',
    is_active BOOLEAN NOT NULL DEFAULT true,
    last_sync_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Create mass_coin_transactions table
CREATE TABLE mass_coin_transactions (
    id BIGSERIAL PRIMARY KEY,
    sender_id VARCHAR(255) NOT NULL,
    recipient_id VARCHAR(255) NOT NULL,
    amount DECIMAL(18,6) NOT NULL,
    transaction_hash VARCHAR(255),
    transaction_type VARCHAR(30) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    gas_fee DECIMAL(18,6),
    usd_value DECIMAL(18,6),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    description TEXT,
    block_number BIGINT,
    FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (recipient_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Create indexes for better performance
CREATE INDEX idx_user_wallets_user_id ON user_wallets(user_id);
CREATE INDEX idx_user_wallets_wallet_address ON user_wallets(wallet_address);
CREATE INDEX idx_user_wallets_balance ON user_wallets(balance);
CREATE INDEX idx_user_wallets_staked_amount ON user_wallets(staked_amount);

CREATE INDEX idx_mass_coin_transactions_sender_id ON mass_coin_transactions(sender_id);
CREATE INDEX idx_mass_coin_transactions_recipient_id ON mass_coin_transactions(recipient_id);
CREATE INDEX idx_mass_coin_transactions_created_at ON mass_coin_transactions(created_at);
CREATE INDEX idx_mass_coin_transactions_transaction_type ON mass_coin_transactions(transaction_type);
CREATE INDEX idx_mass_coin_transactions_status ON mass_coin_transactions(status);
CREATE INDEX idx_mass_coin_transactions_hash ON mass_coin_transactions(transaction_hash);

-- Insert system user for rewards and tips
INSERT INTO users (id, username, email, password, name, created_at, updated_at)
VALUES (
    'system',
    'system',
    'system@maschat.com',
    '$2a$10$systempasswordhash',
    'MasChat System',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
) ON CONFLICT (id) DO NOTHING;

-- Create system wallet
INSERT INTO user_wallets (user_id, wallet_address, balance, staked_amount, total_earned, total_spent, wallet_type, is_active, created_at)
VALUES (
    'system',
    '0x0000000000000000000000000000000000000000',
    1000000000.000000, -- 1 billion MASS initial supply
    0.000000,
    1000000000.000000,
    0.000000,
    'CUSTODIAL',
    true,
    CURRENT_TIMESTAMP
) ON CONFLICT (user_id) DO NOTHING; 