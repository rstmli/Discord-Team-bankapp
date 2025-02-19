package az.payment.payment2.service.impl;

import az.payment.payment2.dao.entity.PaymentEntity;
import az.payment.payment2.dao.repository.PaymentRepository;
import az.payment.payment2.dto.PaymentRequestDto;
import az.payment.payment2.dto.StatusResponse;
import az.payment.payment2.service.PaymentService;
import az.payment.payment2.util.enums.PaymentStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository repository;
    private StatusResponse statusResponse;

    @Override
    @Transactional
    public StatusResponse transferBalance(PaymentRequestDto requestDto) {
        var sender = repository.findByAccountNumber(requestDto.getSenderAccount());
        var receiver = repository.findByAccountNumber(requestDto.getReceiverAccount());
        if (sender.isPresent() && receiver.isPresent()) {
            PaymentEntity senderData = sender.get();
            PaymentEntity receiverData = receiver.get();
            /*
            yoxlayiriq databasadeki balance requestden gelen balansdan boyuk ve ya beraberdimi
            beraberdise ve boyukduse true
            kicikdirse false qayidacaq
             */
            log.warn("hesablar tapildi!");
            if (senderData.getBalance().compareTo(requestDto.getBalance()) >= 0) {
                // pulu gonder ve databasaya yaz
                senderData.setBalance(senderData.getBalance().subtract(requestDto.getBalance()));
                receiverData.setBalance(receiverData.getBalance().add(requestDto.getBalance()));
                repository.save(senderData);
                repository.save(receiverData);
                log.warn("Gonderildi!");

            } else {
                // balansa kifayet etmir
            }

            return null;

        } else {
            log.warn("salamlar");
        }
        return null;
    }
}
