package az.payment.payment.exception;

public class KeyNotFoundException extends RuntimeException {
    public KeyNotFoundException(String msg){
        super(msg);
    }
}
