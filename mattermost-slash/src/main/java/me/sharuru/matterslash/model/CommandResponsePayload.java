package me.sharuru.matterslash.model;

import lombok.Data;

@Data
public class CommandResponsePayload {

    private String response_type = "ephemeral";
    private String text;
    private String username;
    private String icon_url;

}
