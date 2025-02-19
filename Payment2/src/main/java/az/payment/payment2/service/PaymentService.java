package az.payment.payment2.service;

import az.payment.payment2.dto.PaymentRequestDto;
import az.payment.payment2.dto.StatusResponse;

public interface PaymentService {
    StatusResponse transferBalance(PaymentRequestDto requestDto);
}
