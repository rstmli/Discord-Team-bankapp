package az.payment.payment2.controller;

import az.payment.payment2.dto.PaymentRequestDto;
import az.payment.payment2.dto.StatusResponse;
import az.payment.payment2.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/payment/")
public class PaymentController {
    private final PaymentService service;
    @PostMapping("/transfer-balance")
    public StatusResponse paymentTransfer(@RequestBody PaymentRequestDto dto){
        return service.transferBalance(dto);
    }
}
