package self.srr.bot.base.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sharuru on 2017/06/05.
 */
@ConfigurationProperties(prefix = "bot.global")
@Data
public class BotConfiguration {

    private String name;

    private String icon;

    private List<String> masters = new ArrayList<>();
}
