package self.srr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import self.srr.bot.base.config.BotConfiguration;

@SpringBootApplication
@EnableConfigurationProperties(BotConfiguration.class)
public class MBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(MBotApplication.class, args);
	}

}
