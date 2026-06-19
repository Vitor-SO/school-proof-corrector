package vitorcode.school_service.dtos;

import vitorcode.school_service.database.entities.PeriodEntity;

import java.util.UUID;

public record PeriodResponse(
        UUID id,
        String name
) {
    public static PeriodResponse fromEntity(PeriodEntity entity) {
        return new PeriodResponse(
                entity.getId(),
                entity.getName()
        );
    }
}
