package self.srr.bot.base.common;

import lombok.Data;

/**
 * Bot request model
 * <p>
 * Created by Sharuru on 2017/04/28.
 */
@Data
public class BotRequestModel {

    // channel id
    private String channel_id;
    // channel name
    private String channel_name;
    // user command
    private String command;
    // wip
    private String response_url;
    // team domain
    private String team_domain;
    // team id
    private String team_id;
    // user input
    private String text;
    // request token
    private String token;
    // user id
    private String user_id;
    // user nickname
    private String user_name;

}
