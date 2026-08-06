-- liquibase formatted sql

-- changeset agrifarms:alter-booking-fields-to-text
-- validCheckSum: ANY
-- validCheckSum: 9:1be1b1059c8802ecd3bcc30b45d2ffd1
-- comment: Alter bookings.notes and bookings.address_text to TEXT
ALTER TABLE bookings ADD COLUMN IF NOT EXISTS notes TEXT;
ALTER TABLE bookings ADD COLUMN IF NOT EXISTS address_text TEXT;
ALTER TABLE bookings ALTER COLUMN notes TYPE TEXT;
ALTER TABLE bookings ALTER COLUMN address_text TYPE TEXT;

