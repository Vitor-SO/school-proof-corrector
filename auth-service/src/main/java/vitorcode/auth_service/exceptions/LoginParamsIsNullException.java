package vitorcode.auth_service.exceptions;

public class LoginParamsIsNullException extends RuntimeException {
    public LoginParamsIsNullException(String message) {
        super(message);
    }
}
