CREATE TABLE IF NOT EXISTS subject (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) UNIQUE NOT NULL,
    answers_key_id uuid,
    teacher_id uuid,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_subject_answers_key FOREIGN KEY (answers_key_id) REFERENCES answers_key(id),
    CONSTRAINT fk_subject_teacher FOREIGN KEY (teacher_id) REFERENCES users(id)
);

INSERT INTO subject (name) VALUES ('Portugues'), ('Matematica'), ('Historia'),
 ('Geografia'), ('Fisica'), ('Quimica'), ('Biologia'), ('Artes'), ('Ingles');

ALTER TABLE teachers ADD COLUMN subject_id uuid;
ALTER TABLE teachers ADD CONSTRAINT fk_teachers_subject FOREIGN KEY (subject_id) REFERENCES subject(id);

ALTER TABLE answers_key ADD COLUMN subject_id uuid;
ALTER TABLE answers_key ADD CONSTRAINT fk_answers_key_subject FOREIGN KEY (subject_id) REFERENCES subject(id);