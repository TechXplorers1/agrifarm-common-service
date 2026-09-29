-- liquibase formatted sql
-- changeset agrifarms:add_name_to_transport_vehicles

ALTER TABLE transport_vehicles ADD COLUMN IF NOT EXISTS name VARCHAR(255);
