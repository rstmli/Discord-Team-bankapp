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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;
import java.time.LocalDateTime;

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
                        .status(PaymentStatus.ACTIVE)
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
    public StatusResponse transferBalance(TransferRequestDto requestDto) {
        var sender = repository.findByAccountNumber(requestDto.getSenderAccount());
        var receiver = repository.findByAccountNumber(requestDto.getReceiverAccount());
        if (sender.isPresent() && receiver.isPresent()) {
            PaymentEntity senderData = sender.get();
            PaymentEntity receiverData = receiver.get();

            if (senderData.getStatus().equals(PaymentStatus.BLOCK)) {
                if (senderData.getBlockTime().isAfter(LocalDateTime.now())) {
                    return blockStatus(senderData);
                } else {
                    return formatAccount(senderData,requestDto);
                }
            } else {

                if (senderData.getTransferRequestCount() >= 5) {
                    AccountBlock(senderData);
                    return blockStatus(senderData);
                } else {
                    if (!senderData.getAccountNumber().equals(receiverData.getAccountNumber())) {

                        if (senderData.getBalance().compareTo(requestDto.getBalance()) >= 0) {
                            senderData.setBalance(senderData.getBalance().subtract(requestDto.getBalance()));
                            receiverData.setBalance(receiverData.getBalance().add(requestDto.getBalance()));
                            repository.save(senderData);
                            repository.save(receiverData);
                            return new StatusResponse(PaymentStatus.SUCCESS, null);

                        } else {
                            transferCountPlus(senderData);
                            throw new BalanceLimitExceededException("Your balance is insufficient.");
                        }

                    } else {
                        transferCountPlus(senderData);
                        throw new SelfTransferException("You cannot transfer money to your own account.");
                    }

                }

            }

        } else {
            throw new UserNotFoundException("not found by AccountNumber");
        }

    }


    private StatusResponse blockStatus(PaymentEntity senderData){
        return new StatusResponse(PaymentStatus.BLOCK,String.format("You have been blocked for %d " +
                "minutes", Duration.between(LocalDateTime.now(), senderData.getBlockTime()).toMinutes()));
    }
    private void AccountBlock(PaymentEntity senderData){
        senderData.setStatus(PaymentStatus.BLOCK);
        senderData.setBlockTime(LocalDateTime.now().plusMinutes(5));
        repository.save(senderData);
    }

    private StatusResponse formatAccount(PaymentEntity senderData, TransferRequestDto requestDto) {
        senderData.setBlockTime(null);
        senderData.setStatus(PaymentStatus.ACTIVE);
        senderData.setTransferRequestCount(0);
        repository.save(senderData);
        return transferBalance(requestDto);

    }

    private void transferCountPlus(PaymentEntity senderData){
        senderData.setTransferRequestCount(senderData.getTransferRequestCount() + 1);
        repository.save(senderData);
    }
}