package vitorcode.school_service.database.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "student")
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    @ManyToOne
    @JoinColumn(name = "school_year_id")
    private SchoolYearEntity schoolYear;
    @ManyToOne
    @JoinColumn(name = "school_grades_id")
    private SchoolGradesEntity schoolGrades;
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

    public SchoolYearEntity getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(SchoolYearEntity schoolYear) {
        this.schoolYear = schoolYear;
    }

    public SchoolGradesEntity getSchoolGrades() {
        return schoolGrades;
    }

    public void setSchoolGrades(SchoolGradesEntity schoolGrades) {
        this.schoolGrades = schoolGrades;
    }
}
