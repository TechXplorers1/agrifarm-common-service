-- liquibase formatted sql

-- changeset agrifarms:add-user-notification-preferences
-- validCheckSum: ANY
ALTER TABLE users ADD COLUMN IF NOT EXISTS notification_order_updates BOOLEAN DEFAULT TRUE;
ALTER TABLE users ADD COLUMN IF NOT EXISTS notification_booking_updates BOOLEAN DEFAULT TRUE;
ALTER TABLE users ADD COLUMN IF NOT EXISTS notification_payment_updates BOOLEAN DEFAULT TRUE;
ALTER TABLE users ADD COLUMN IF NOT EXISTS notification_community_activity BOOLEAN DEFAULT FALSE;
ALTER TABLE users ADD COLUMN IF NOT EXISTS notification_promotional_offers BOOLEAN DEFAULT FALSE;
