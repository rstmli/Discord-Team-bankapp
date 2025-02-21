package az.payment.payment2.mapper;

import az.payment.payment2.dao.entity.PaymentEntity;
import az.payment.payment2.dto.PaymentRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface PaymentMapper {
    PaymentEntity dtoToEntity(PaymentRequestDto dto);
}
