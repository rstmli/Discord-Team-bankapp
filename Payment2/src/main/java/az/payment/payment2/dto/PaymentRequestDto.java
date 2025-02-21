package az.payment.payment2.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.math.BigDecimal;
@Component
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Validated
public class PaymentRequestDto implements Serializable {
    private static final long serialVersionUID = 42L;
    @Size(max = 255,message = "enter max 255 symbol")
    String name;
    String surname;
    @Min(value = 0,message = "min balance 0")
    BigDecimal balance;

}
