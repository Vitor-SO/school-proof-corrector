package vitorcode.school_service.dtos;

import java.util.UUID;

public record SubjectFilter(
        UUID id,
        String name,
        UUID teacherId
) {
}
