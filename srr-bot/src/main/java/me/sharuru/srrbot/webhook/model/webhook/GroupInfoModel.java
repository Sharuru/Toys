package me.sharuru.srrbot.webhook.model.webhook;

import lombok.Data;

/**
 * QQ群信息对象
 */
@Data
public class GroupInfoModel {

    private long id;
    private String name;
    private String permission;

}
