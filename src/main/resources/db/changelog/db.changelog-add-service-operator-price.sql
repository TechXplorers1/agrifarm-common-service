-- liquibase formatted sql

-- changeset agrifarms:add-operator-price-to-service-offerings
ALTER TABLE service_offerings ADD COLUMN IF NOT EXISTS operator_price DOUBLE PRECISION;
