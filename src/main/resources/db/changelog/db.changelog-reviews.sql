-- liquibase formatted sql

-- changeset agrifarms:reviews-1
-- validCheckSum: ANY
CREATE TABLE IF NOT EXISTS reviews (
    id VARCHAR(255) PRIMARY KEY,
    booking_id VARCHAR(255) NOT NULL,
    asset_id VARCHAR(255) NOT NULL,
    reviewer_id VARCHAR(255) NOT NULL,
    rating INT NOT NULL,
    comment TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
