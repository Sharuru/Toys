package me.sharuru.ldt;

import lombok.extern.slf4j.Slf4j;
import me.sharuru.ldt.core.LdtMain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class LdtApplication implements CommandLineRunner {

    @Autowired
    LdtMain ldtMain;

    public static void main(String[] args) {
        SpringApplication.run(LdtApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("LDT is starting...");
        ldtMain.main();

    }
}
