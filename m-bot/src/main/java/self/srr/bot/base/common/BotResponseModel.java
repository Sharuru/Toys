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


    /**
     * Change response visible range
     *
     * @param flag is public
     */
    public void setIsPublic(boolean flag) {
        if (flag) {
            this.response_type = BotContrast.BOT_RESP_TYPE_ICH;
        } else {
            this.response_type = BotContrast.BOT_RESP_TYPE_EPH;
        }
    }

    /**
     * Append '@' mark at the end
     *
     * @param who username
     */
    public void appendAtWho(String who) {
        this.text += "  @" + who;
    }

}
