CREATE TABLE IF NOT EXISTS subject (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO subject (name) VALUES ('Portugues'), ('Matematica'), ('Historia'),
 ('Geografia'), ('Fisica'), ('Quimica'), ('Biologia'), ('Artes'), ('Ingles');
