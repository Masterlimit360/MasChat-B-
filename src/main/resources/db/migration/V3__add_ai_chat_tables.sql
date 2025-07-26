-- Create AI chat sessions table
CREATE TABLE IF NOT EXISTS ai_chats (
    id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    session_id VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_activity TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    message_count INTEGER NOT NULL DEFAULT 0,
    is_active BOOLEAN NOT NULL DEFAULT TRUE
);

-- Create AI chat messages table
CREATE TABLE IF NOT EXISTS ai_chat_messages (
    id SERIAL PRIMARY KEY,
    ai_chat_id BIGINT NOT NULL REFERENCES ai_chats(id) ON DELETE CASCADE,
    content TEXT NOT NULL,
    is_user_message BOOLEAN NOT NULL,
    sent_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ai_response_time_ms BIGINT,
    tokens_used INTEGER,
    model_used VARCHAR(100),
    error_message TEXT
);

-- Create indexes for better performance
CREATE INDEX IF NOT EXISTS idx_ai_chats_user_id ON ai_chats(user_id);
CREATE INDEX IF NOT EXISTS idx_ai_chats_session_id ON ai_chats(session_id);
CREATE INDEX IF NOT EXISTS idx_ai_chats_last_activity ON ai_chats(last_activity);
CREATE INDEX IF NOT EXISTS idx_ai_chat_messages_ai_chat_id ON ai_chat_messages(ai_chat_id);
CREATE INDEX IF NOT EXISTS idx_ai_chat_messages_sent_at ON ai_chat_messages(sent_at);
CREATE INDEX IF NOT EXISTS idx_ai_chat_messages_is_user_message ON ai_chat_messages(is_user_message);

-- Add comments for documentation
COMMENT ON TABLE ai_chats IS 'Stores AI chat sessions for each user';
COMMENT ON TABLE ai_chat_messages IS 'Stores individual messages in AI chat conversations';
COMMENT ON COLUMN ai_chats.session_id IS 'Unique session identifier for each chat';
COMMENT ON COLUMN ai_chat_messages.ai_response_time_ms IS 'Response time in milliseconds for AI messages';
COMMENT ON COLUMN ai_chat_messages.tokens_used IS 'Number of tokens used in the AI response';
COMMENT ON COLUMN ai_chat_messages.model_used IS 'AI model used for generating the response'; 