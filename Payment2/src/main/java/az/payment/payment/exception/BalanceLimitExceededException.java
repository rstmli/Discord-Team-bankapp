package az.payment.payment.exception;

public class BalanceLimitExceededException extends RuntimeException {
  public BalanceLimitExceededException(String message) {
    super(message);
  }
}
