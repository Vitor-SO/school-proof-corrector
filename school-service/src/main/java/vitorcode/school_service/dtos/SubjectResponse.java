package vitorcode.school_service.dtos;

import vitorcode.school_service.database.entities.SubjectEntity;

import java.util.UUID;

public record SubjectResponse(
        UUID id,
        String name
) {
    public static SubjectResponse fromEntity(SubjectEntity entity) {
        return new SubjectResponse(
                entity.getId(),
                entity.getName()
        );
    }
}
