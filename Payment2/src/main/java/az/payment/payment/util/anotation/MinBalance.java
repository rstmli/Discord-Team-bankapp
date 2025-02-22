package az.payment.payment.util.anotation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.BigDecimal;

@Constraint(validatedBy = MinBalanceValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface MinBalance {
    String message() default "Balance must be at least 0.01";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String value() default "0.01";
}

class MinBalanceValidator implements ConstraintValidator<MinBalance, BigDecimal>{


    private BigDecimal minBalance;

    @Override
    public void initialize(MinBalance constraintAnnotation) {
        this.minBalance = new BigDecimal(constraintAnnotation.value());
    }

    @Override
    public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return value.compareTo(minBalance) >= 0;
    }
}
