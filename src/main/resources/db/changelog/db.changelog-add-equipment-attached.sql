-- liquibase formatted sql

-- changeset agrifarms:add_equipment_attached_equipments
ALTER TABLE equipment ADD COLUMN IF NOT EXISTS attached_equipments VARCHAR(500);
