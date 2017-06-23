package self.srr.bot.biz.translate.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration map of the translate
 * <p>
 * Created by Sharuru on 2017/06/23.
 */
@ConfigurationProperties(prefix = "bot.translate")
@Data
public class TranslateConfiguration {

    private String appid;

    private String secret;

    private String url;
}
