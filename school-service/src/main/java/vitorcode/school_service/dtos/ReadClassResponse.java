package vitorcode.school_service.dtos;

import vitorcode.school_service.database.entities.ClassEntity;

import java.time.Instant;
import java.util.UUID;

public record ReadClassResponse(
        UUID id,
        String name,
        Instant createdAt,
        Instant updatedAt
) {
    public static ReadClassResponse fromEntity(ClassEntity entity){
        return new ReadClassResponse(
                entity.getId(),
                entity.getName(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
