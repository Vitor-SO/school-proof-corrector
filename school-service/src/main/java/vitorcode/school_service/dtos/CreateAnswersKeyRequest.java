package vitorcode.school_service.dtos;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CreateAnswersKeyRequest(
        @NotNull(message = "Disciplina é obrigatório")
        UUID subjectId,
        @NotNull(message = "Nota por questão é obrigatória")
        BigDecimal gradePerQuestion,
        @NotNull(message = "Respostas são obrigatórias")
        List<Answer> answers
) {
}
