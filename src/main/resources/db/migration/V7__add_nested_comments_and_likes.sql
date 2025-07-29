-- Add parent_comment_id column for nested comments
ALTER TABLE comments ADD COLUMN parent_comment_id BIGINT;
ALTER TABLE comments ADD CONSTRAINT fk_comment_parent FOREIGN KEY (parent_comment_id) REFERENCES comments(id);

-- Add index for better performance on parent comment lookups
CREATE INDEX idx_comments_parent_id ON comments(parent_comment_id);

-- Add index for better performance on user lookups
CREATE INDEX idx_comments_user_id ON comments(user_id);

-- Add index for better performance on post lookups
CREATE INDEX idx_comments_post_id ON comments(post_id);

-- Add index for better performance on created_at lookups
CREATE INDEX idx_comments_created_at ON comments(created_at); 