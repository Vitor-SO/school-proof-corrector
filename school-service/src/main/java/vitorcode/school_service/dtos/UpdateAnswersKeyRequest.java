package vitorcode.school_service.dtos;

import jakarta.validation.constraints.NotNull;
import vitorcode.school_service.database.entities.AnswersKeyEntity;

import java.math.BigDecimal;
import java.util.List;

public record UpdateAnswersKeyRequest(
        @NotNull(message = "Nota por questão é obrigatória")
        BigDecimal gradePerQuestion,
        @NotNull(message = "Respostas são obrigatórias")
        List<UpdateListAnswersKeyRequest> answers
) {
}
