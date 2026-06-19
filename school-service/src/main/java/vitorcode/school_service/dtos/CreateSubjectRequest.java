package vitorcode.school_service.dtos;

import jakarta.validation.constraints.NotBlank;

public record CreateSubjectRequest(
        @NotBlank String name
) {
}
