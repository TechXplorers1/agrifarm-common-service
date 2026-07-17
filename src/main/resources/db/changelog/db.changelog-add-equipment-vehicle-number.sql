--liquibase formatted sql
--changeset csc:add-equipment-vehicle-number
ALTER TABLE equipment ADD COLUMN IF NOT EXISTS vehicle_number VARCHAR(255);
