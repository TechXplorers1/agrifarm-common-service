-- liquibase formatted sql

-- changeset agrifarms:add-user-status-and-admin
-- validCheckSum: ANY

-- 1. Add status column to users table
ALTER TABLE users ADD COLUMN IF NOT EXISTS status VARCHAR(50) DEFAULT 'Active';

-- 2. Ensure an 'admin' user exists to avoid Foreign Key violations in notifications
INSERT INTO users (user_id, full_name, phone_number, role, status)
SELECT 'admin', 'System Admin', '0000000000', 'ADMIN', 'Active'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE user_id = 'admin');
