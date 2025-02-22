package az.payment.payment.exception;

import az.payment.payment.dto.RedisStatusResponse;
import az.payment.payment.dto.StatusResponse;
import az.payment.payment.util.enums.PaymentStatus;
import az.payment.payment.util.enums.RedisStatus;
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

    @ExceptionHandler(value = BalanceEnoughFundsExceptions.class)
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public StatusResponse thereAreNotEnoughFunds(BalanceEnoughFundsExceptions ex) {
        return new StatusResponse(PaymentStatus.INSUFFICIENT_BALANCE, ex.getMessage());
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public StatusResponse NotFoundUser(UserNotFoundException ex) {
        return new StatusResponse(PaymentStatus.NOT_FOUND_USER, ex.getMessage());
    }

    @ExceptionHandler(value = SelfTransferException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public StatusResponse SelfTransferExceptions(SelfTransferException exception){
        return new StatusResponse(PaymentStatus.FAIL,exception.getMessage());
    }

    @ExceptionHandler(value = BalanceLimitExceededException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public StatusResponse BalanceEnoughFundsExceptions(BalanceLimitExceededException e){
        return new StatusResponse(PaymentStatus.FAIL,e.getMessage());
    }

    @ExceptionHandler(value = KeyNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public RedisStatusResponse keyNotFoundExceptionHandler(KeyNotFoundException e){
        return new RedisStatusResponse(RedisStatus.FAIL,e.getMessage());
    }
    // BlockAccountException
    @ExceptionHandler(value = BlockAccountException.class)
    @ResponseStatus(HttpStatus.LOCKED)
    public StatusResponse accountBlockExceptionHandler(BlockAccountException e){
        return new StatusResponse(PaymentStatus.BLOCK,e.getMessage());
    }

}
