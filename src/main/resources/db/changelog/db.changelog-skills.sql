-- liquibase formatted sql

-- changeset agrifarms:create-skills-table
CREATE TABLE IF NOT EXISTS skills (
    id SERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL UNIQUE
);

-- changeset agrifarms:insert-default-skills
INSERT INTO skills (name) VALUES ('Harvesting') ON CONFLICT (name) DO NOTHING;
INSERT INTO skills (name) VALUES ('Ploughing') ON CONFLICT (name) DO NOTHING;
INSERT INTO skills (name) VALUES ('Sowing') ON CONFLICT (name) DO NOTHING;
INSERT INTO skills (name) VALUES ('Weeding') ON CONFLICT (name) DO NOTHING;
INSERT INTO skills (name) VALUES ('Transplanting') ON CONFLICT (name) DO NOTHING;
INSERT INTO skills (name) VALUES ('Pruning') ON CONFLICT (name) DO NOTHING;
INSERT INTO skills (name) VALUES ('Spraying') ON CONFLICT (name) DO NOTHING;
