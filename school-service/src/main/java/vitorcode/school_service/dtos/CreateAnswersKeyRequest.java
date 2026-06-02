package vitorcode.school_service.dtos;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CreateAnswersKeyRequest(
        UUID subjectId,
        BigDecimal gradePerQuestion,
        List<Answer> answers
) {
}
