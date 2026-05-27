package vitorcode.school_service.dtos;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record CreateSubjectRequest(
        @NotBlank String name,
        @NotBlank UUID classId
) {
}
