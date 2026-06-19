package vitorcode.school_service.config;

import java.util.UUID;

public record TeacherFilter(
        UUID id,
        String name,
        UUID userId,
        UUID classId,
        String className,
        UUID subjectId,
        String subjectName
){}