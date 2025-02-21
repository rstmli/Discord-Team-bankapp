package az.payment.payment.mapper;

import az.payment.payment.dao.entity.PaymentEntity;
import az.payment.payment.dto.PaymentRequestDto;
import az.payment.payment.dto.PaymentResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface PaymentMapper {
    PaymentEntity dtoToEntity(PaymentRequestDto dto);
    List<PaymentResponseDto> entityToListEntity(List<PaymentEntity> entities);
}
