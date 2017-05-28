package self.srr.common;

import lombok.Data;

/**
 * Bot 响应实体
 * <p>
 * Created by Sharuru on 2017/04/28.
 */
@Data
public class BotResponseModel {

    // 响应类型
    private String response_type = BotContrast.BOT_RES_TYPE_EPH;
    // 响应内容
    private String text = BotContrast.BOT_RESPONSE;
    // Bot 名
    private String username = BotContrast.BOT_NAME;
    // Bot 头像地址
    private String icon_url = BotContrast.BOT_ICON_URL;

}
