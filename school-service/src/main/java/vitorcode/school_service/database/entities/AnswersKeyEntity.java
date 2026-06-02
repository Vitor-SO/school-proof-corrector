package vitorcode.school_service.database.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "answers_key")
public class AnswersKeyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "grade_per_question", precision = 4, scale = 2, nullable = false)
    private BigDecimal gradePerQuestion;

    @Column(nullable = false, unique = true)
    private Integer question;

    private String answer;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private SubjectEntity subject;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BigDecimal getGradePerQuestion() {
        return gradePerQuestion;
    }

    public void setGradePerQuestion(BigDecimal gradePerQuestion) {
        this.gradePerQuestion = gradePerQuestion;
    }

    public Integer getQuestion() {
        return question;
    }

    public void setQuestion(Integer question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public SubjectEntity getSubject() {
        return subject;
    }

    public void setSubject(SubjectEntity subject) {
        this.subject = subject;
    }
}
