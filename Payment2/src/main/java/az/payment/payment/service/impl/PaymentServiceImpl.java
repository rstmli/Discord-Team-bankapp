package az.payment.payment.service.impl;

import az.payment.payment.dao.entity.PaymentEntity;
import az.payment.payment.dao.repository.PaymentRepository;
import az.payment.payment.dto.PaymentRequestDto;
import az.payment.payment.dto.TransferRequestDto;
import az.payment.payment.dto.StatusResponse;
import az.payment.payment.exception.BalanceLimitExceededException;
import az.payment.payment.exception.SelfTransferException;
import az.payment.payment.exception.UserNotFoundException;
import az.payment.payment.service.PaymentService;
import az.payment.payment.util.enums.PaymentStatus;
import az.payment.payment.util.helper.AccountNumberGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Slf4j
@RequiredArgsConstructor
@Validated
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository repository;
    private final AccountNumberGenerator generator;
    private Integer count = 0;

    @Override
    public void addUser(PaymentRequestDto dto) {

        while (true) {

            try {
                ++count;
                String accountNumber = generator.codeGenerator();
                var entity = PaymentEntity.builder()
                        .name(dto.getName())
                        .surname(dto.getSurname())
                        .accountNumber(accountNumber)
                        .balance(dto.getBalance()).build();
                repository.save(entity);
                return;

            } catch (DataIntegrityViolationException e) {

                if (count >= 3) {
                    throw new UserNotFoundException("hesab yaradila bilmir destekle elaqeye kecin");
                }
            }
        }
    }

    @Override
    @Transactional
    public StatusResponse transferBalance(TransferRequestDto requestDto) {
        var sender = repository.findByAccountNumber(requestDto.getSenderAccount());
        var receiver = repository.findByAccountNumber(requestDto.getReceiverAccount());
        if (sender.isPresent() && receiver.isPresent()) {
            PaymentEntity senderData = sender.get();
            PaymentEntity receiverData = receiver.get();

            if (!senderData.getAccountNumber().equals(receiverData.getAccountNumber())) {

                if (senderData.getBalance().compareTo(requestDto.getBalance()) >= 0) {
                    senderData.setBalance(senderData.getBalance().subtract(requestDto.getBalance()));
                    receiverData.setBalance(receiverData.getBalance().add(requestDto.getBalance()));
                    repository.save(senderData);
                    repository.save(receiverData);
                    return new StatusResponse(PaymentStatus.SUCCESS, null);

                } else {
                    throw new BalanceLimitExceededException("balansinizda kifayet qeder vesait yoxdur");
                }

            } else {
                throw new SelfTransferException("You cannot transfer money to your own account.");
            }

        } else {
            throw new UserNotFoundException("not found by AccountNumber");
        }
    }

}
