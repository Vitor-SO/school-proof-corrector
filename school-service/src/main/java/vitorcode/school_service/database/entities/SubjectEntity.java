package vitorcode.school_service.database.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "subject")
public class SubjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "answers_key_id")
    private AnswersKeyEntity answersKey;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private TeachersEntity teacher;

    @Column(nullable = false, name = "created_at")
    @CreationTimestamp
    private Instant createdAt;

    @Column(nullable = false, name = "updated_at")
    @UpdateTimestamp
    private Instant updatedAt;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public AnswersKeyEntity getAnswersKey() {
        return answersKey;
    }

    public void setAnswersKey(AnswersKeyEntity answersKey) {
        this.answersKey = answersKey;
    }

    public TeachersEntity getTeacher() {
        return teacher;
    }

    public void setTeacher(TeachersEntity teacher) {
        this.teacher = teacher;
    }
}
