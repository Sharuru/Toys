package me.sharuru.srrbot;

import me.sharuru.srrbot.business.authorization.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class SrrBotApplication {

    @Autowired
    AuthorizationService loginComponent;

    public static void main(String[] args) {
        SpringApplication.run(SrrBotApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        loginComponent.refreshSessionKey();
    }

}
