package cc.casually.singin.jq22;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class Task {

    @Scheduled(cron = "0 0 8 * * *")
    public void task() {
        new SingInAll().run();
    }
}
