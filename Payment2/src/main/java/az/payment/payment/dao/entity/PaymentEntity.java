package az.payment.payment.dao.entity;

import az.payment.payment.util.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payments")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String name;
    String surname;
    @Column(unique = true,nullable = false)
    String accountNumber;
    BigDecimal balance;
    @Builder.Default
    Integer transferRequestCount = 0;
    LocalDateTime blockTime;
    PaymentStatus status;
}
