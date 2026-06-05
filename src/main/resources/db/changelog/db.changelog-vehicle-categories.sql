-- liquibase formatted sql

-- changeset agrifarms:create-vehicle-categories-table
CREATE TABLE IF NOT EXISTS vehicle_categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL UNIQUE
);

-- changeset agrifarms:insert-default-vehicle-categories
INSERT INTO vehicle_categories (name) VALUES ('Tractor') ON CONFLICT (name) DO NOTHING;
INSERT INTO vehicle_categories (name) VALUES ('Harvester') ON CONFLICT (name) DO NOTHING;
INSERT INTO vehicle_categories (name) VALUES ('JCB') ON CONFLICT (name) DO NOTHING;
INSERT INTO vehicle_categories (name) VALUES ('Mini Truck') ON CONFLICT (name) DO NOTHING;
INSERT INTO vehicle_categories (name) VALUES ('Tractor Trolley') ON CONFLICT (name) DO NOTHING;
INSERT INTO vehicle_categories (name) VALUES ('Pickup Truck') ON CONFLICT (name) DO NOTHING;

-- changeset agrifarms:add-operator-price-to-vehicles
ALTER TABLE transport_vehicles ADD COLUMN IF NOT EXISTS operator_price DOUBLE PRECISION;

-- changeset agrifarms:add-new-fields-to-transport-vehicles
ALTER TABLE transport_vehicles ADD COLUMN IF NOT EXISTS owner_business_name VARCHAR(255);
ALTER TABLE transport_vehicles ADD COLUMN IF NOT EXISTS brand VARCHAR(150);
ALTER TABLE transport_vehicles ADD COLUMN IF NOT EXISTS model VARCHAR(150);
ALTER TABLE transport_vehicles ADD COLUMN IF NOT EXISTS year_of_manufacture INTEGER;
ALTER TABLE transport_vehicles ADD COLUMN IF NOT EXISTS price_per_km DOUBLE PRECISION;
ALTER TABLE transport_vehicles ADD COLUMN IF NOT EXISTS price_per_hour DOUBLE PRECISION;
ALTER TABLE transport_vehicles ADD COLUMN IF NOT EXISTS vehicle_condition VARCHAR(50);
