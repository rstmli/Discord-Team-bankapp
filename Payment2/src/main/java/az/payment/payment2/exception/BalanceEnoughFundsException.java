package az.payment.payment2.exception;

public class BalanceEnoughFundsException extends RuntimeException {
    public BalanceEnoughFundsException(String message) {
        super(message);
    }
}
