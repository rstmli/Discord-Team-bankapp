package az.payment.payment2.mapper;

import az.payment.payment2.dao.entity.PaymentEntity;
import az.payment.payment2.dto.PaymentRequestDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-21T01:03:38+0400",
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
}
