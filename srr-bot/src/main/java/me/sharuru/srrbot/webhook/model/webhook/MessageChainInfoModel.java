package me.sharuru.srrbot.webhook.model.webhook;

import lombok.Data;

/**
 * 信息链对象
 */
@Data
public class MessageChainInfoModel {

    private String type;
    private long id;
    private long time;
    private String text;
    private String url;

}
