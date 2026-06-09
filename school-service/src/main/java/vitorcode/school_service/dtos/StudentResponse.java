package vitorcode.school_service.dtos;

import vitorcode.school_service.database.entities.StudentsEntity;

import java.util.UUID;

public record StudentResponse(
        UUID id,
        String name,
        String className,
        String academicLevel
) {
    public static StudentResponse fromEntity(StudentsEntity entity){
        return new StudentResponse(
                entity.getId(),
                entity.getName(),
                entity.getClassEntity() != null
                        ? entity.getClassEntity().getName()
                        : null,
                entity.getAcademicLevel() != null
                        ? entity.getAcademicLevel().getName()
                        : null
        );
    }
}
