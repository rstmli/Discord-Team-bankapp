package az.payment.payment.dto;

import az.payment.payment.util.anotation.MinBalance;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
@Component
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Validated
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransferRequestDto {
    @JsonProperty("sender")
    String senderAccount;
    @JsonProperty("receiver")
    String receiverAccount;
    @MinBalance
    BigDecimal balance;

}
