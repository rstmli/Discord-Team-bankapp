package az.payment.payment2.queue;

import az.payment.payment2.dto.PaymentRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Producer {
    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(PaymentRequestDto dto) {
        // PaymentRequestDto obyektini JSON formatında "useradd" queue-ya göndəririk
        rabbitTemplate.convertAndSend("userssssss", dto);
    }
}