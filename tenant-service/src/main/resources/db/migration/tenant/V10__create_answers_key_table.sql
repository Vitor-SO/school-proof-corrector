CREATE TABLE IF NOT EXISTS answers_key (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    grade_per_question DECIMAL(4,2) NOT NULL,
    question INTEGER UNIQUE NOT NULL,
    answer VARCHAR(1),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    subject_id UUID NOT NULL,
    CONSTRAINT fk_answers_key_subject FOREIGN KEY (subject_id) REFERENCES subject(id)
);