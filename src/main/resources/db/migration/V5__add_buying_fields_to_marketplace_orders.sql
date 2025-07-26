-- Add buying fields to marketplace_orders table (with existence checks)
DO $$ 
BEGIN
    -- Add item_id column if it doesn't exist
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name = 'marketplace_orders' AND column_name = 'item_id') THEN
        ALTER TABLE marketplace_orders ADD COLUMN item_id BIGINT;
    END IF;
    
    -- Add buyer_id column if it doesn't exist
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name = 'marketplace_orders' AND column_name = 'buyer_id') THEN
        ALTER TABLE marketplace_orders ADD COLUMN buyer_id BIGINT;
    END IF;
    
    -- Add seller_id column if it doesn't exist
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name = 'marketplace_orders' AND column_name = 'seller_id') THEN
        ALTER TABLE marketplace_orders ADD COLUMN seller_id BIGINT;
    END IF;
    
    -- Add quantity column if it doesn't exist
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name = 'marketplace_orders' AND column_name = 'quantity') THEN
        ALTER TABLE marketplace_orders ADD COLUMN quantity INTEGER DEFAULT 1;
    END IF;
    
    -- Add total_amount column if it doesn't exist
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name = 'marketplace_orders' AND column_name = 'total_amount') THEN
        ALTER TABLE marketplace_orders ADD COLUMN total_amount DECIMAL(10,2);
    END IF;
    
    -- Add shipping_address column if it doesn't exist
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name = 'marketplace_orders' AND column_name = 'shipping_address') THEN
        ALTER TABLE marketplace_orders ADD COLUMN shipping_address TEXT;
    END IF;
    
    -- Add phone_number column if it doesn't exist
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name = 'marketplace_orders' AND column_name = 'phone_number') THEN
        ALTER TABLE marketplace_orders ADD COLUMN phone_number VARCHAR(20);
    END IF;
END $$;

-- Add indexes for better performance (with existence checks)
DO $$
BEGIN
    -- Add buyer_id index if it doesn't exist
    IF NOT EXISTS (SELECT 1 FROM pg_indexes WHERE tablename = 'marketplace_orders' AND indexname = 'idx_marketplace_orders_buyer_id') THEN
        CREATE INDEX idx_marketplace_orders_buyer_id ON marketplace_orders(buyer_id);
    END IF;
    
    -- Add seller_id index if it doesn't exist
    IF NOT EXISTS (SELECT 1 FROM pg_indexes WHERE tablename = 'marketplace_orders' AND indexname = 'idx_marketplace_orders_seller_id') THEN
        CREATE INDEX idx_marketplace_orders_seller_id ON marketplace_orders(seller_id);
    END IF;
    
    -- Add item_id index if it doesn't exist
    IF NOT EXISTS (SELECT 1 FROM pg_indexes WHERE tablename = 'marketplace_orders' AND indexname = 'idx_marketplace_orders_item_id') THEN
        CREATE INDEX idx_marketplace_orders_item_id ON marketplace_orders(item_id);
    END IF;
END $$; 