-- liquibase formatted sql

-- =====================================================
-- changeset agrihub:1.1.0
-- Create USERS
-- =====================================================
CREATE TABLE IF NOT EXISTS users (
    user_id VARCHAR(50) PRIMARY KEY,
    phone_number VARCHAR(20) NOT NULL UNIQUE,
    full_name VARCHAR(150),
    role VARCHAR(50),
    district VARCHAR(100),
    village VARCHAR(100),
    profile_image_url VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

-- =====================================================
-- changeset agrihub:1.1.1
-- Create SERVICES (Old Name)
-- =====================================================
CREATE TABLE IF NOT EXISTS services (
    id VARCHAR(50) PRIMARY KEY,
    user_id VARCHAR(50) NOT NULL,
    service_name VARCHAR(150) NOT NULL,
    description TEXT,
    price_per_day DOUBLE PRECISION,
    available BOOLEAN DEFAULT TRUE,
    image_url VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_services_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- =====================================================
-- changeset agrihub:1.1.2
-- Create EQUIPMENT
-- =====================================================
CREATE TABLE IF NOT EXISTS equipment (
    id VARCHAR(50) PRIMARY KEY,
    user_id VARCHAR(50) NOT NULL,
    name VARCHAR(150) NOT NULL,
    description TEXT,
    price_per_day DOUBLE PRECISION,
    available BOOLEAN DEFAULT TRUE,
    image_url VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_equipment_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- =====================================================
-- changeset agrihub:1.1.3
-- Create TRANSPORT_VEHICLES
-- =====================================================
CREATE TABLE IF NOT EXISTS transport_vehicles (
    id VARCHAR(50) PRIMARY KEY,
    user_id VARCHAR(50) NOT NULL,
    vehicle_type VARCHAR(100) NOT NULL,
    capacity VARCHAR(100),
    price_per_km DOUBLE PRECISION,
    available BOOLEAN DEFAULT TRUE,
    image_url VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_transport_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- =====================================================
-- changeset agrihub:1.1.4
-- Create WORKER_GROUPS
-- =====================================================
CREATE TABLE IF NOT EXISTS worker_groups (
    id VARCHAR(50) PRIMARY KEY,
    user_id VARCHAR(50) NOT NULL,
    group_name VARCHAR(150) NOT NULL,
    location VARCHAR(150),
    available BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_worker_group_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- =====================================================
-- changeset agrihub:1.1.5
-- Create WORKER_GROUP_ROLES
-- =====================================================
CREATE TABLE IF NOT EXISTS worker_group_roles (
    id SERIAL PRIMARY KEY,
    group_id VARCHAR(50) NOT NULL,
    role_name VARCHAR(100) NOT NULL,
    gender VARCHAR(20),
    count INTEGER DEFAULT 1,
    daily_wage DOUBLE PRECISION,
    CONSTRAINT fk_roles_group FOREIGN KEY (group_id) REFERENCES worker_groups(id)
);

-- =====================================================
-- changeset agrihub:1.1.6
-- Create BOOKINGS
-- =====================================================
CREATE TABLE IF NOT EXISTS bookings (
    id VARCHAR(50) PRIMARY KEY,
    user_id VARCHAR(50) NOT NULL,
    asset_id VARCHAR(50) NOT NULL,
    asset_type VARCHAR(50) NOT NULL,
    booking_date DATE NOT NULL,
    status VARCHAR(50) DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_bookings_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- =====================================================
-- changeset agrihub:1.1.7
-- Add status columns to assets
-- =====================================================
ALTER TABLE services ADD COLUMN IF NOT EXISTS status VARCHAR(50) DEFAULT 'AVAILABLE';
ALTER TABLE equipment ADD COLUMN IF NOT EXISTS status VARCHAR(50) DEFAULT 'AVAILABLE';
ALTER TABLE transport_vehicles ADD COLUMN IF NOT EXISTS status VARCHAR(50) DEFAULT 'AVAILABLE';
ALTER TABLE worker_groups ADD COLUMN IF NOT EXISTS status VARCHAR(50) DEFAULT 'AVAILABLE';

-- =====================================================
-- changeset agrihub:1.1.8
-- Add contact details to assets
-- =====================================================
ALTER TABLE services ADD COLUMN IF NOT EXISTS contact_number VARCHAR(20);
ALTER TABLE equipment ADD COLUMN IF NOT EXISTS contact_number VARCHAR(20);
ALTER TABLE transport_vehicles ADD COLUMN IF NOT EXISTS contact_number VARCHAR(20);
ALTER TABLE worker_groups ADD COLUMN IF NOT EXISTS contact_number VARCHAR(20);

-- =====================================================
-- changeset agrihub:1.1.9
-- Add location details to assets
-- =====================================================
ALTER TABLE services ADD COLUMN IF NOT EXISTS district VARCHAR(100);
ALTER TABLE services ADD COLUMN IF NOT EXISTS village VARCHAR(100);
ALTER TABLE equipment ADD COLUMN IF NOT EXISTS district VARCHAR(100);
ALTER TABLE equipment ADD COLUMN IF NOT EXISTS village VARCHAR(100);
ALTER TABLE transport_vehicles ADD COLUMN IF NOT EXISTS district VARCHAR(100);
ALTER TABLE transport_vehicles ADD COLUMN IF NOT EXISTS village VARCHAR(100);
ALTER TABLE worker_groups ADD COLUMN IF NOT EXISTS district VARCHAR(100);
ALTER TABLE worker_groups ADD COLUMN IF NOT EXISTS village VARCHAR(100);

-- =====================================================
-- changeset agrihub:1.1.10
-- Create USER_NOTIFICATIONS
-- =====================================================
CREATE TABLE IF NOT EXISTS user_notifications (
    id VARCHAR(50) PRIMARY KEY,
    user_id VARCHAR(50) NOT NULL,
    title VARCHAR(255),
    message TEXT,
    type VARCHAR(100),
    related_id VARCHAR(50),
    is_read BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_user_notifications_user
        FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- =====================================================
-- changeset agrihub:1.1.11 validCheckSum:9:8ba2debb3bdf17568db2727d5241cbb2
-- Rename services to service_offerings
-- =====================================================
ALTER TABLE services RENAME TO service_offerings;
