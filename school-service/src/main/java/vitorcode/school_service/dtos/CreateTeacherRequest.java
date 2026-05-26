package vitorcode.school_service.dtos;

import java.util.UUID;

public record CreateTeacherRequest(
        String name,
        String email,
        String password,
        UUID roleId
) {
}
