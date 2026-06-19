CREATE TABLE IF NOT EXISTS bulletin(
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    student_id uuid NOT NULL,
    subject_id uuid NOT NULL,
    academic_level_id uuid NOT NULL,
    period_id uuid NOT NULL,
    grade DECIMAL(4,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_bulletin_student FOREIGN KEY (student_id) REFERENCES students(id),
    CONSTRAINT fk_bulletin_subject FOREIGN KEY (subject_id) REFERENCES subject(id),
    CONSTRAINT fk_bulletin_academic_level FOREIGN KEY (academic_level_id) REFERENCES academic_level(id),
    CONSTRAINT fk_bulletin_period FOREIGN KEY (period_id) REFERENCES period(id)
);