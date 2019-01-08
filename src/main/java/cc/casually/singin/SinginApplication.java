package cc.casually.singin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SinginApplication {

    public static void main(String[] args) {
        SpringApplication.run(SinginApplication.class, args);
    }

}

