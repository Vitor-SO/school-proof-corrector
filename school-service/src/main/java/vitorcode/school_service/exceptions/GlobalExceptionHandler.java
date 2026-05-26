package vitorcode.school_service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CreateTeacherParamsIsNullException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleCreateTeacherParamsIsNullException(CreateTeacherParamsIsNullException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("message error: ", ex.getMessage());

        return error;
    }

    @ExceptionHandler(CreateTeacherException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Map<String, String> handleCreateTeacherException(CreateTeacherException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("message error: ", ex.getMessage());

        return error;
    }
}
