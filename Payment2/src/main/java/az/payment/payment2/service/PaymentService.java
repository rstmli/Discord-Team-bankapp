package az.payment.payment2.service;

import az.payment.payment2.dto.PaymentRequestDto;
import az.payment.payment2.dto.TransferRequestDto;
import az.payment.payment2.dto.StatusResponse;

public interface PaymentService {
    void addUser(PaymentRequestDto dto);
    StatusResponse transferBalance(TransferRequestDto requestDto);
    StatusResponse rabbitMqExampleTest(PaymentRequestDto msg);
}
