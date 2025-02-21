package az.payment.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableRedisRepositories
public class Payment2Application {

    public static void main(String[] args) {
        SpringApplication.run(Payment2Application.class, args);
    }

}
