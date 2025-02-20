package az.payment.payment2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransferRequestDto {
    @JsonProperty("sender")
    String senderAccount;
    @JsonProperty("receiver")
    String receiverAccount;
    BigDecimal balance;
}
