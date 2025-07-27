-- Create groups table
CREATE TABLE groups (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    is_private BOOLEAN NOT NULL DEFAULT FALSE,
    category VARCHAR(100) NOT NULL DEFAULT 'General',
    image_url VARCHAR(500) NOT NULL DEFAULT 'https://i.imgur.com/default-group.png',
    created_by BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_active BOOLEAN NOT NULL DEFAULT TRUE
);

-- Create group_members table
CREATE TABLE group_members (
    id BIGSERIAL PRIMARY KEY,
    group_id BIGINT NOT NULL REFERENCES groups(id) ON DELETE CASCADE,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    role VARCHAR(20) NOT NULL DEFAULT 'MEMBER' CHECK (role IN ('ADMIN', 'MODERATOR', 'MEMBER')),
    joined_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    UNIQUE(group_id, user_id)
);

-- Create indexes for better performance
CREATE INDEX idx_groups_created_by ON groups(created_by);
CREATE INDEX idx_groups_category ON groups(category);
CREATE INDEX idx_groups_is_private ON groups(is_private);
CREATE INDEX idx_groups_is_active ON groups(is_active);
CREATE INDEX idx_group_members_group_id ON group_members(group_id);
CREATE INDEX idx_group_members_user_id ON group_members(user_id);
CREATE INDEX idx_group_members_role ON group_members(role);
CREATE INDEX idx_group_members_is_active ON group_members(is_active);

-- Add some sample groups for testing
INSERT INTO groups (name, description, is_private, category, created_by) VALUES
('MasChat Developers', 'Official group for MasChat app developers and contributors', FALSE, 'Technology', 1),
('Photography Enthusiasts', 'Share your best photos and get feedback from fellow photographers', FALSE, 'Photography', 1),
('Travel Stories', 'Share your travel experiences and discover amazing destinations', FALSE, 'Travel', 1),
('Book Club', 'Monthly book discussions and reading recommendations', TRUE, 'Books', 1),
('Fitness Motivation', 'Stay motivated with workout tips and progress sharing', FALSE, 'Fitness', 1);

-- Add sample group members
INSERT INTO group_members (group_id, user_id, role) VALUES
(1, 1, 'ADMIN'),
(2, 1, 'ADMIN'),
(3, 1, 'ADMIN'),
(4, 1, 'ADMIN'),
(5, 1, 'ADMIN'); 