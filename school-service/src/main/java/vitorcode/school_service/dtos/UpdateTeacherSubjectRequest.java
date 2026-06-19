package vitorcode.school_service.dtos;

import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.UUID;

public record UpdateTeacherSubjectRequest(
        @NotBlank UUID id,
        List<UUID> subjectsId
) {
}
