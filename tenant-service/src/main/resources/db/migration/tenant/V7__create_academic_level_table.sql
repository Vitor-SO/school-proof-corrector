CREATE TABLE IF NOT EXISTS academic_level(
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO academic_level (name) VALUES ('9 Ano'), ('1 Ano Medio'), ('2 Ano Medio'),
  ('3 Ano Medio');