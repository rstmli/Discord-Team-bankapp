package az.payment.payment.util.helper;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.UUID;

@Component
public class AccountNumberGenerator {
    public String codeGenerator() {
        return String.format("AZ%09d", Math.abs(UUID.randomUUID().getMostSignificantBits()) % 1_000_000_000L);
    }
}
