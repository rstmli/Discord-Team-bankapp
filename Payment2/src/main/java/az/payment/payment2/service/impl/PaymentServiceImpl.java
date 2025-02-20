package az.payment.payment2.service.impl;

import az.payment.payment2.dao.entity.PaymentEntity;
import az.payment.payment2.dao.repository.PaymentRepository;
import az.payment.payment2.dto.PaymentRequestDto;
import az.payment.payment2.dto.TransferRequestDto;
import az.payment.payment2.dto.StatusResponse;
import az.payment.payment2.exception.BalanceEnoughFundsExceptions;
import az.payment.payment2.exception.BalanceLimitExceededException;
import az.payment.payment2.exception.SelfTransferException;
import az.payment.payment2.exception.UserNotFoundException;
import az.payment.payment2.mapper.PaymentMapper;
import az.payment.payment2.service.PaymentService;
import az.payment.payment2.util.enums.PaymentStatus;
import az.payment.payment2.util.helper.AccountNumberGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository repository;
    private final PaymentMapper mapper;
    private final AccountNumberGenerator generator;

    @Override
    public void addUser(PaymentRequestDto dto) {
        boolean isSaved = false;
        int count = 0;
        while (!isSaved){
            try{
                ++count;
                String accountNumber = generator.codeGenerator();
                var entity = PaymentEntity.builder()
                        .name(dto.getName())
                        .surname(dto.getSurname())
                        .accountNumber(accountNumber)
                        .balance(dto.getBalance()).build();
                repository.save(entity);
                return;
            }catch (DataIntegrityViolationException e){
                if(count>=10){
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
                    return new StatusResponse(PaymentStatus.SUCCESS,null);

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
