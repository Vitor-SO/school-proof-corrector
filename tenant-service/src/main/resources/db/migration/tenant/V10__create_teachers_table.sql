CREATE TABLE IF NOT EXISTS teachers(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    user_id UUID NOT NULL,
    class_id UUID,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_teachers_users FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_teachers_class FOREIGN KEY (class_id) REFERENCES class(id)
);