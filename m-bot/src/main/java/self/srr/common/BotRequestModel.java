package self.srr.common;

import lombok.Data;

/**
 * Bot 请求实体
 * <p>
 * Created by Sharuru on 2017/04/28.
 */
@Data
public class BotRequestModel {

    // 频道 ID
    private String channel_id;
    // 频道名称
    private String channel_name;
    // 指令
    private String command;
    // 响应 URL
    private String response_url;
    // 团队域
    private String team_domain;
    // 团队 ID
    private String team_id;
    // 指令内容
    private String text;
    // 命令 token
    private String token;
    // 用户 ID
    private String user_id;
    // 用户名
    private String user_name;

}
