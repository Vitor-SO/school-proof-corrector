package vitorcode.school_service.dtos;

import vitorcode.school_service.database.entities.StudentsEntity;
import vitorcode.school_service.database.entities.TeachersEntity;

import java.util.List;
import java.util.UUID;

public record TeacherResponse(
        UUID id,
        String name,
        UUID userId,
        ClassResponse classResponse,
        List<SubjectResponse> subjectResponse
) {
    public static TeacherResponse fromEntity(TeachersEntity entity){
        return new TeacherResponse(
                entity.getId(),
                entity.getName(),
                entity.getUser().getId(),
                entity.getClassEntity() != null
                        ? new ClassResponse(
                        entity.getClassEntity().getId(),
                        entity.getClassEntity().getName()
                )
                        : null,
                entity.getSubjects() != null
                        ? entity.getSubjects()
                          .stream()
                          .map(subject -> new SubjectResponse(
                                  subject.getId(),
                                  subject.getName()
                          ))
                          .toList()
                        : List.of()
        );
    }
}
