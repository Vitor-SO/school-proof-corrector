package vitorcode.tenant_service.exceptions;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        Map<String, String> error = new HashMap<>();
        Throwable cause = ex.getCause();

        if (cause instanceof InvalidFormatException) {
            InvalidFormatException ife = (InvalidFormatException) cause;
            String fieldName = ife.getPath().get(0).getFieldName();
            String valueType = ife.getTargetType().getSimpleName();
            String invalidValue = ife.getValue().toString();

            error.put("error", String.format("Invalid value '%s' for field '%s'. Expected type: %s.",
                    invalidValue, fieldName, valueType));
        } else {
            error.put("error", "Malformed JSON request.");
        }

        return error;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Map<String, String> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("message error: ", ex.getMessage());

        return error;
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("message error: ", ex.getMessage());

        return error;
    }

    @ExceptionHandler(SchoolSqlInsertDbException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleSchoolSqlInsertDbException(SchoolSqlInsertDbException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("message error: ", ex.getMessage());

        return error;
    }

    @ExceptionHandler(UserSqlInsertDbException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleUserSqlInsertDbException(UserSqlInsertDbException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("message error: ", ex.getMessage());

        return error;
    }

    @ExceptionHandler(MasterSqlTenantInsertDbException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleMasterSqlTenantInsertDbException(MasterSqlTenantInsertDbException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("message error: ", ex.getMessage());

        return error;
    }

    @ExceptionHandler(EmailIsNullException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleEmailIsNullException(EmailIsNullException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("message error: ", ex.getMessage());

        return error;
    }

    @ExceptionHandler(LoginParamsIsNullException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String,String> handleLoginParamsIsNullException(LoginParamsIsNullException ex){
        Map<String,String> error = new HashMap<>();
        error.put("message: ", ex.getMessage());

        return error;
    }

    @ExceptionHandler(EmailNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public Map<String,String> handleEmailNotFoundException(EmailNotFoundException ex){
        Map<String,String> error = new HashMap<>();
        error.put("message: ", ex.getMessage());

        return error;
    }

    @ExceptionHandler(SlugNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public Map<String,String> handleSlugNotFoundException(SlugNotFoundException ex){
        Map<String,String> error = new HashMap<>();
        error.put("message: ", ex.getMessage());

        return error;
    }

    @ExceptionHandler(TenantSqlSelectUserByEmailException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Map<String,String> handleTenantSqlSelectUserByEmailException(TenantSqlSelectUserByEmailException ex){
        Map<String,String> error = new HashMap<>();
        error.put("message: ", ex.getMessage());

        return error;
    }

    @ExceptionHandler(TenantPasswordDecryptException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Map<String,String> handleTenantPasswordDecryptException(TenantPasswordDecryptException ex){
        Map<String,String> error = new HashMap<>();
        error.put("message: ", ex.getMessage());

        return error;
    }
}
