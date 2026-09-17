-- liquibase formatted sql

-- changeset agrifarms:reports-1
CREATE TABLE IF NOT EXISTS reports (
    id VARCHAR(255) PRIMARY KEY,
    reporter_user_id VARCHAR(255) NOT NULL,
    reported_item_id VARCHAR(255) NOT NULL,
    reported_item_name VARCHAR(255),
    reported_provider_id VARCHAR(255) NOT NULL,
    reason VARCHAR(500) NOT NULL,
    details VARCHAR(2000),
    blocked BOOLEAN DEFAULT false,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
