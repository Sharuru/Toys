package self.srr.bot.base.common;

import lombok.Data;

/**
 * Bot response model
 * <p>
 * Created by Sharuru on 2017/04/28.
 */
@Data
public class BotResponseModel {

    // response type
    private String response_type;
    // response context
    private String text;
    // Bot name
    private String username;
    // Bot icon address
    private String icon_url;

}
