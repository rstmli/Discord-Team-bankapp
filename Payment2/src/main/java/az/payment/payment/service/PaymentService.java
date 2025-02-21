package az.payment.payment.service;

import az.payment.payment.dto.PaymentRequestDto;
import az.payment.payment.dto.TransferRequestDto;
import az.payment.payment.dto.StatusResponse;

public interface PaymentService {
    void addUser(PaymentRequestDto dto);
    StatusResponse transferBalance(TransferRequestDto requestDto);
}
