package az.payment.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
@Data
@Builder
@Component
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponseDto {
    String name;
    String surname;
    String accountNumber;
    BigDecimal balance;
}
