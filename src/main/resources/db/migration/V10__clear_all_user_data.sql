-- Clear all user-related data
-- This migration will remove all users and their associated data

-- Clear notifications first (they reference users)
DELETE FROM notifications;

-- Clear mass coin transfer requests (they reference users)
DELETE FROM mass_coin_transfer_requests;

-- Clear mass coin transactions (they reference users)
DELETE FROM mass_coin_transactions;

-- Clear user wallets (they reference users)
DELETE FROM user_wallets;

-- Clear AI chat messages (they reference users)
DELETE FROM ai_chat_messages;

-- Clear AI chats (they reference users)
DELETE FROM ai_chats;

-- Clear messages (they reference chats)
DELETE FROM messages;

-- Clear chats (they reference users)
DELETE FROM chats;

-- Clear friend requests (they reference users)
DELETE FROM friend_requests;

-- Clear post likes (they reference users)
DELETE FROM likes;

-- Clear post comments (they reference users)
DELETE FROM comments;

-- Clear posts (they reference users)
DELETE FROM posts;

-- Clear reel likes (they reference users)
-- (No explicit table, handled by reels/likes join or similar)

-- Clear reel comments (they reference users)
-- (No explicit table, handled by reels/comments join or similar)

-- Clear reels (they reference users)
DELETE FROM reels;

-- Clear stories (they reference users)
DELETE FROM stories;

-- Clear marketplace orders (they reference users)
DELETE FROM marketplace_orders;

-- Clear marketplace items (they reference users)
DELETE FROM marketplace_items;

-- Clear marketplace reviews (they reference users)
-- (No explicit table, handled by reviews or comments join or similar)

-- Clear marketplace business accounts (they reference users)
-- (No explicit table, handled by business accounts join or similar)

-- Clear group members (they reference users)
DELETE FROM group_members;

-- Clear groups (they reference users)
DELETE FROM groups;

-- Finally, clear all users
DELETE FROM users;

-- Reset auto-increment counters
ALTER SEQUENCE users_id_seq RESTART WITH 1;
ALTER SEQUENCE user_wallets_id_seq RESTART WITH 1;
ALTER SEQUENCE mass_coin_transactions_id_seq RESTART WITH 1;
ALTER SEQUENCE mass_coin_transfer_requests_id_seq RESTART WITH 1;
ALTER SEQUENCE posts_id_seq RESTART WITH 1;
ALTER SEQUENCE reels_id_seq RESTART WITH 1;
ALTER SEQUENCE stories_id_seq RESTART WITH 1;
ALTER SEQUENCE chats_id_seq RESTART WITH 1;
-- ALTER SEQUENCE chat_messages_id_seq RESTART WITH 1; -- Table does not exist
ALTER SEQUENCE notifications_id_seq RESTART WITH 1; 