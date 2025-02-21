package az.payment.payment.dto;
import az.payment.payment.util.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
public record StatusResponse(PaymentStatus status,@JsonInclude(JsonInclude.Include.NON_NULL) String message) {

}
