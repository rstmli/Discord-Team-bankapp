package az.payment.payment.dto;

import az.payment.payment.util.enums.RedisStatus;

public record RedisStatusResponse(RedisStatus status, String msg) {
}
