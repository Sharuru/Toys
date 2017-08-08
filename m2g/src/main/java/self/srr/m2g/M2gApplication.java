package self.srr.m2g;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class M2gApplication implements CommandLineRunner {

	@Autowired
    MainLogic mainLogic;

	public static void main(String[] args) {
		SpringApplication.run(M2gApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
        mainLogic.run(args);
	}
}
