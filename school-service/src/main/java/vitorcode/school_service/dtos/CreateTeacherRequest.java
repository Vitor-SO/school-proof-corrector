package vitorcode.school_service.dtos;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record CreateTeacherRequest(
        @NotBlank(message = "Name é obrigatório")
        String name,
        @NotBlank(message = "Email é obrigatorio")
        String email,
        @NotBlank(message = "Password é obrigatorio")
        String password,
        UUID subjectId
) {
}
