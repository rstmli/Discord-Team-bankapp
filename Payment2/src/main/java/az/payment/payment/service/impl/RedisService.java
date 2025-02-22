package az.payment.payment.service.impl;

import az.payment.payment.dao.repository.PaymentRepository;
import az.payment.payment.dto.PaymentResponseDto;
import az.payment.payment.dto.RedisStatusResponse;
import az.payment.payment.exception.KeyNotFoundException;
import az.payment.payment.mapper.PaymentMapper;
import az.payment.payment.util.enums.RedisStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
@Validated
public class RedisService {
    private final RedisTemplate<String, List<PaymentResponseDto>> redisTemplate;
    private final PaymentRepository repository;
    private final PaymentMapper paymentMapper;

    public void saveToCache(String key, List<PaymentResponseDto> data, long timeoutMinutes) {
        redisTemplate.opsForValue().set(key, data, timeoutMinutes, TimeUnit.MINUTES);
    }

    public List<PaymentResponseDto> getFromCache(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public RedisStatusResponse deleteFromCache(String key) {

        if (redisTemplate.delete(key)){
            redisTemplate.delete(key);
            return new RedisStatusResponse(RedisStatus.SUCCESS,"cache cleared successfully");
        }else{
            throw new KeyNotFoundException("No results matching the key were found");
        }
    }

    public List<PaymentResponseDto> getUser(String key){
        List<PaymentResponseDto> cachedData = getFromCache(key);
        if(cachedData != null){
            return cachedData;
        }
        List<PaymentResponseDto> dbData = paymentMapper.entityToListEntity( repository.findAll());
        saveToCache(key, dbData, 10);
        log.info("The result came from the database");

        return dbData;
    }

}