package az.payment.payment2.controller;

import az.payment.payment2.dto.PaymentRequestDto;
import az.payment.payment2.dto.TransferRequestDto;
import az.payment.payment2.dto.StatusResponse;
import az.payment.payment2.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/payment/")
public class PaymentController {
    private final PaymentService service;

    @PostMapping("/post")
    public void postUser(@Valid @RequestBody PaymentRequestDto dto){
        service.addUser(dto);
    }

    @PostMapping("/transfer-balance")
    public StatusResponse paymentTransfer(@RequestBody TransferRequestDto dto){
        return service.transferBalance(dto);
    }
}
