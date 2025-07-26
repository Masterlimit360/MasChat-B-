-- Add constraints to ensure seller_id is never null
ALTER TABLE marketplace_items ALTER COLUMN seller_id SET NOT NULL;

-- Add foreign key constraint to ensure seller_id references a valid user
ALTER TABLE marketplace_items 
ADD CONSTRAINT fk_marketplace_items_seller 
FOREIGN KEY (seller_id) REFERENCES users(id) ON DELETE CASCADE;

-- Add constraint to ensure category_id references a valid category
ALTER TABLE marketplace_items 
ADD CONSTRAINT fk_marketplace_items_category 
FOREIGN KEY (category_id) REFERENCES marketplace_categories(id) ON DELETE SET NULL;

-- Update any existing items with null seller_id to use the first available user
UPDATE marketplace_items 
SET seller_id = (SELECT id FROM users LIMIT 1) 
WHERE seller_id IS NULL;

-- Ensure no items can have null seller_id after the update
ALTER TABLE marketplace_items ALTER COLUMN seller_id SET NOT NULL; 