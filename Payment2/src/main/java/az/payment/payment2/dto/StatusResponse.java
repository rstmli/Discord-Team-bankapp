package az.payment.payment2.dto;
import az.payment.payment2.util.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
public record StatusResponse(PaymentStatus status,@JsonInclude(JsonInclude.Include.NON_NULL) String message) {

}
