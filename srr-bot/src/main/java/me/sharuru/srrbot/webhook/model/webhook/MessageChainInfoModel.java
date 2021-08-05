package me.sharuru.srrbot.webhook.model.webhook;

import lombok.Data;

@Data
public class MessageChainInfoModel {

    private String type;
    private long id;
    private long time;
    private String text;

}
