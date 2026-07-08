-- liquibase formatted sql

-- changeset agrifarms:increase-task-length-and-rename-columns-v2 splitStatements:false
-- validCheckSum: ANY
DO $$ 
BEGIN
    -- Rename id to role_id if id exists
    IF EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name='worker_group_roles' AND column_name='id') THEN
        ALTER TABLE worker_group_roles RENAME COLUMN id TO role_id;
    END IF;

    -- Rename role_name to task_name if role_name exists
    IF EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name='worker_group_roles' AND column_name='role_name') THEN
        ALTER TABLE worker_group_roles RENAME COLUMN role_name TO task_name;
    END IF;
END $$;

ALTER TABLE worker_group_roles ALTER COLUMN task_name TYPE VARCHAR(1000);
