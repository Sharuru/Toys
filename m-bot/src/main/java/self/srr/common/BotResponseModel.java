package self.srr.common;

/**
 * Bot 响应实体
 * <p>
 * Created by Sharuru on 2017/04/28.
 */
public class BotResponseModel {

    // 响应类型
    private String response_type = BotContrast.BOT_RES_TYPE_EPH;
    // 响应内容
    private String text = BotContrast.BOT_RESPONSE;
    // Bot 名
    private String username = BotContrast.BOT_NAME;
    // Bot 头像地址
    private String icon_url = BotContrast.BOT_ICON_URL;

    public String getResponse_type() {
        return response_type;
    }

    public void setResponse_type(String response_type) {
        this.response_type = response_type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }
}
