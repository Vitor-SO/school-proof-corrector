package vitorcode.school_service.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateStudentRequest(
        @NotBlank(message = "Nome do estudante é obrigatório")
        String name,
        @NotNull(message = "Ano letivo é obrigatório")
        UUID schoolYearId
) {
}
