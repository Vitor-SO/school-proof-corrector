package vitorcode.school_service.dtos;

import jakarta.validation.constraints.NotBlank;

public record UpdateClassRequest(
        @NotBlank String name
) {
}
