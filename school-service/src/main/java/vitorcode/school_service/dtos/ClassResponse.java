package vitorcode.school_service.dtos;

import java.util.UUID;

public record ClassResponse(
        UUID id,
        String name
) {
}
