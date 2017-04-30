package self.srr.common;

/**
 * Bot 请求实体
 * <p>
 * Created by Sharuru on 2017/04/28.
 */
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

    public String getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    public String getChannel_name() {
        return channel_name;
    }

    public void setChannel_name(String channel_name) {
        this.channel_name = channel_name;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getResponse_url() {
        return response_url;
    }

    public void setResponse_url(String response_url) {
        this.response_url = response_url;
    }

    public String getTeam_domain() {
        return team_domain;
    }

    public void setTeam_domain(String team_domain) {
        this.team_domain = team_domain;
    }

    public String getTeam_id() {
        return team_id;
    }

    public void setTeam_id(String team_id) {
        this.team_id = team_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
