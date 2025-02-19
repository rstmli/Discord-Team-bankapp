package az.payment.payment2.exception;

import az.payment.payment2.dto.StatusResponse;
import az.payment.payment2.util.enums.PaymentStatus;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ExceptionHandler(value = BalanceEnoughFundsException.class)
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public StatusResponse thereAreNotEnoughFunds(BalanceEnoughFundsException ex) {
        return new StatusResponse(PaymentStatus.INSUFFICIENT_BALANCE, ex.getMessage());
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public StatusResponse NotFoundUser(UserNotFoundException ex) {
        return new StatusResponse(PaymentStatus.NOT_FOUND_USER, ex.getMessage());
    }


}
