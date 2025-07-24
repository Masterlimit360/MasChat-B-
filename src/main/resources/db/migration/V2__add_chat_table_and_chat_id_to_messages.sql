-- Create chats table
CREATE TABLE IF NOT EXISTS chats (
    id SERIAL PRIMARY KEY,
    user1_id BIGINT NOT NULL REFERENCES users(id),
    user2_id BIGINT NOT NULL REFERENCES users(id),
    CONSTRAINT unique_users UNIQUE (user1_id, user2_id)
);

-- Add chat_id to messages
ALTER TABLE messages ADD COLUMN IF NOT EXISTS chat_id BIGINT;

-- If you have existing data, you may need to backfill chat_id for old messages here
-- For new installs, you can skip this step

-- Make chat_id NOT NULL and add foreign key
ALTER TABLE messages ALTER COLUMN chat_id SET NOT NULL;
ALTER TABLE messages ADD CONSTRAINT fk_chat FOREIGN KEY (chat_id) REFERENCES chats(id); 