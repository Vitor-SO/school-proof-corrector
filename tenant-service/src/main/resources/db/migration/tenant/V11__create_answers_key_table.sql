CREATE TABLE IF NOT EXISTS answers_key (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    grade_per_question DECIMAL(4,2) NOT NULL,
    question INTEGER UNIQUE NOT NULL,
    answer VARCHAR(1)
);