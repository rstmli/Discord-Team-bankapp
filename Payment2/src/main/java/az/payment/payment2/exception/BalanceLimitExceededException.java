package az.payment.payment2.exception;

public class BalanceLimitExceededException extends RuntimeException {
  public BalanceLimitExceededException(String message) {
    super(message);
  }
}
