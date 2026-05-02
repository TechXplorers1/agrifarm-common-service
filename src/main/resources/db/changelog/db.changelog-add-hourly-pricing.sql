-- liquibase formatted sql

-- changeset agrifarms:add-hourly-pricing-to-worker-groups

ALTER TABLE worker_groups ADD COLUMN price_per_male_hourly DECIMAL(10, 2);
ALTER TABLE worker_groups ADD COLUMN price_per_female_hourly DECIMAL(10, 2);
