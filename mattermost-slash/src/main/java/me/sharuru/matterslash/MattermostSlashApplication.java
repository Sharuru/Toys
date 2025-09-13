package me.sharuru.matterslash;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MattermostSlashApplication {

    public static void main(String[] args) {
        SpringApplication.run(MattermostSlashApplication.class, args);
    }

}
