package vitorcode.school_service.database.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "bulletin")
public class BulletinEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentsEntity student;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private SubjectEntity subject;

    @ManyToOne
    @JoinColumn(name = "academic_level_id")
    private AcademicLevelEntity academicLevel;

    @ManyToOne
    @JoinColumn(name = "period_id")
    private PeriodEntity period;

    @Column(precision = 4, scale = 2, nullable = false)
    private BigDecimal grade;

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

    public BigDecimal getGrade() {
        return grade;
    }

    public void setGrade(BigDecimal grade) {
        this.grade = grade;
    }

    public SubjectEntity getSubject() {
        return subject;
    }

    public void setSubject(SubjectEntity subject) {
        this.subject = subject;
    }

    public PeriodEntity getPeriod() {
        return period;
    }

    public void setPeriod(PeriodEntity period) {
        this.period = period;
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

    public AcademicLevelEntity getAcademicLevel() {
        return academicLevel;
    }

    public void setAcademicLevel(AcademicLevelEntity academicLevel) {
        this.academicLevel = academicLevel;
    }

    public StudentsEntity getStudent() {
        return student;
    }

    public void setStudent(StudentsEntity student) {
        this.student = student;
    }
}
