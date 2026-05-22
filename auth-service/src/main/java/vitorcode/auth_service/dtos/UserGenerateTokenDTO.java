package vitorcode.auth_service.dtos;

public record UserGenerateTokenDTO(
        String tenant,
        String email,
        String role
) {
}
