package vitorcode.school_service.dtos;

import java.util.UUID;

public record CreateCorrectorRequest(
        UUID classId,
        UUID subjectId,
        UUID periodId,
        UUID studentId
) {
}
