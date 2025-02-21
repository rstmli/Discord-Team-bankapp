package az.payment.payment.controller;

import az.payment.payment.dto.*;
import az.payment.payment.service.PaymentService;
import az.payment.payment.service.impl.RedisService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/payment/")
public class PaymentController {
    private final PaymentService service;
    private final RedisService redisService;

    @PostMapping("/post")
    public void postUser(@Valid @RequestBody PaymentRequestDto dto) {
        service.addUser(dto);
    }

    @PostMapping("/transfer-balance")
    public StatusResponse paymentTransfer(@RequestBody TransferRequestDto dto) {
        return service.transferBalance(dto);
    }

    @GetMapping("alluser")
    public List<PaymentResponseDto> getusers(){
        return redisService.getUser("users");
    }
    @DeleteMapping("/delete-cache")
    public RedisStatusResponse deleteCache(@RequestParam String key){
        return redisService.deleteFromCache(key);
    }
}
