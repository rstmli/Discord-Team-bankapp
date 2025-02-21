package az.payment.payment.util.helper;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class AccountNumberGenerator {
    public String codeGenerator(){
        String accountNumber;
        SecureRandom random = new SecureRandom();
        var otp = random.nextLong(999999999);
        accountNumber = String.format("AZ%09d",otp);
        return accountNumber;




    }
}
