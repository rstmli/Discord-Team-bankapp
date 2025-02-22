package az.payment.payment.mapper;

import az.payment.payment.dao.entity.PaymentEntity;
import az.payment.payment.dto.PaymentRequestDto;
import az.payment.payment.dto.PaymentResponseDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-23T02:41:09+0400",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.12.1.jar, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class PaymentMapperImpl implements PaymentMapper {

    @Override
    public PaymentEntity dtoToEntity(PaymentRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        PaymentEntity.PaymentEntityBuilder paymentEntity = PaymentEntity.builder();

        paymentEntity.name( dto.getName() );
        paymentEntity.surname( dto.getSurname() );
        paymentEntity.balance( dto.getBalance() );

        return paymentEntity.build();
    }

    @Override
    public List<PaymentResponseDto> entityToListEntity(List<PaymentEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<PaymentResponseDto> list = new ArrayList<PaymentResponseDto>( entities.size() );
        for ( PaymentEntity paymentEntity : entities ) {
            list.add( paymentEntityToPaymentResponseDto( paymentEntity ) );
        }

        return list;
    }

    protected PaymentResponseDto paymentEntityToPaymentResponseDto(PaymentEntity paymentEntity) {
        if ( paymentEntity == null ) {
            return null;
        }

        PaymentResponseDto.PaymentResponseDtoBuilder paymentResponseDto = PaymentResponseDto.builder();

        paymentResponseDto.name( paymentEntity.getName() );
        paymentResponseDto.surname( paymentEntity.getSurname() );
        paymentResponseDto.accountNumber( paymentEntity.getAccountNumber() );
        paymentResponseDto.balance( paymentEntity.getBalance() );

        return paymentResponseDto.build();
    }
}
