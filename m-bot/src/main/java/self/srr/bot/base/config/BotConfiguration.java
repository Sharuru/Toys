package self.srr.bot.base.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Sharuru on 2017/06/05.
 */
@ConfigurationProperties(prefix = "bot")
public class BotConfiguration {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
