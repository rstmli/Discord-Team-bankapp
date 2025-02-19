package az.payment.payment2.dto;

import az.payment.payment2.util.enums.PaymentStatus;

public record StatusResponse(PaymentStatus status,String message) {

}
