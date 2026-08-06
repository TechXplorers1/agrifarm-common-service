-- liquibase formatted sql

-- changeset agrifarms:alter-booking-fields-to-text
-- comment: Alter bookings.notes and bookings.address_text to TEXT
-- validCheckSum: ANY
ALTER TABLE bookings ADD COLUMN IF NOT EXISTS notes TEXT;
ALTER TABLE bookings ADD COLUMN IF NOT EXISTS address_text TEXT;
ALTER TABLE bookings ALTER COLUMN notes TYPE TEXT;
ALTER TABLE bookings ALTER COLUMN address_text TYPE TEXT;

