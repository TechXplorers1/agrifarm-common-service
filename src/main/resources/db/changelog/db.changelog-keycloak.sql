-- liquibase formatted sql

-- changeset keycloak:1
ALTER TABLE users ADD COLUMN keycloak_id VARCHAR(255) UNIQUE;
