package me.sharuru.mattermost.sum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class MsumApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsumApplication.class, args);
    }

}
