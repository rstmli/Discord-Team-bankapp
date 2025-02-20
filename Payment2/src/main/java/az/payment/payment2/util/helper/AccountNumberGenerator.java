package az.payment.payment2.util.helper;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class AccountNumberGenerator {
    public String codeGenerator(){
        String accountNumber;
        SecureRandom random = new SecureRandom();
        var otp = random.nextLong(2);
        accountNumber = String.format("AZ%09d",otp);
        return accountNumber;




    }
}
