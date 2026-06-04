package vitorcode.school_service.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateGradeRequest(
        @NotNull(message = "O id do aluno é obrigatório")
        UUID studentId,
        @NotNull(message = "A matéria é obrigatória")
        UUID subjectId,
        @NotNull(message = "O periodo escolar é obrigatório")
        UUID periodId
) {
}
