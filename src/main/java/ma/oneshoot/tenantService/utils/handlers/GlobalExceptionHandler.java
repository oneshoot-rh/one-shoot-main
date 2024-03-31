package ma.oneshoot.tenantService.utils.handlers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ma.oneshoot.tenantService.utils.exceptions.TenantDBInitException;
import ma.oneshoot.tenantService.utils.exceptions.TenantNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TenantNotFoundException.class)
    public ResponseEntity<Object> handleTenantNotFoundException(TenantNotFoundException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    record ErrorResponse(String message) {
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        e.printStackTrace();
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler({TenantDBInitException.class,RuntimeException.class})
    public ResponseEntity<ErrorResponse> handleTnDbInit(Exception ex){
        ex.printStackTrace();
        return ResponseEntity.internalServerError().body(new ErrorResponse(ex.getMessage()));
    }
}
