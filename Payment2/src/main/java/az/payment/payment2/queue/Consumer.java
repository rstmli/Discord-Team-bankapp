package az.payment.payment2.queue;

import az.payment.payment2.dao.repository.PaymentRepository;
import az.payment.payment2.mapper.PaymentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Consumer {
    private final RabbitTemplate rabbitTemplate;
    private final PaymentRepository repository;
    private final PaymentMapper paymentMapper;

//    public String writeDatabase(PaymentRequestDto dto){
//        return "salam";
//    }
}
