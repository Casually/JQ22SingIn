package cc.casually.singin.jq22;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Task {

    @Scheduled(cron = "0 0 1 * * *")
    public void task(){
        try {
            SingInAll.singinAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
