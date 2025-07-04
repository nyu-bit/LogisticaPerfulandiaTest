package cl.perfulandia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsPedidoBsApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsPedidoBsApplication.class, args);
    }
}
