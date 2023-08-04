package me.sharuru.matterslash.model;

import lombok.Data;

@Data
public class CommandRequestPayload {

    private String channel_id;
    private String channel_name;
    private String command;
    private String response_url;
    private String team_domain;
    private String team_id;
    private String text;
    private String token;
    private String user_id;
    private String user_name;

}
