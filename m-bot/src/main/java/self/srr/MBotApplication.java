package self.srr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import self.srr.bot.base.config.BotConfiguration;
import self.srr.bot.biz.translate.config.TranslateConfiguration;

@SpringBootApplication
@EnableConfigurationProperties({BotConfiguration.class, TranslateConfiguration.class})
public class MBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(MBotApplication.class, args);
    }

}
