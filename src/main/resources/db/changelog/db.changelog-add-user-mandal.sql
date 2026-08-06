-- liquibase formatted sql

-- changeset agrifarms:add-user-mandal
ALTER TABLE users ADD COLUMN IF NOT EXISTS mandal VARCHAR(255);
