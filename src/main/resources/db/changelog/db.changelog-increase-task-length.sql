-- liquibase formatted sql

-- changeset agrifarms:increase-task-length-in-worker-group-roles
-- validCheckSum: ANY
ALTER TABLE worker_group_roles ALTER COLUMN task_name TYPE VARCHAR(1000);
