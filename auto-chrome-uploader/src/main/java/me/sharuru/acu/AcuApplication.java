package me.sharuru.acu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class AcuApplication implements CommandLineRunner {

    @Autowired
    AcuCore acuCore;

    public static void main(String[] args) {
        SpringApplication.run(AcuApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        acuCore.main();
    }
}
