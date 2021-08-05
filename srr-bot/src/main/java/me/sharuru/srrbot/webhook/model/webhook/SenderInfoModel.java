package me.sharuru.srrbot.webhook.model.webhook;

import lombok.Data;

@Data
public class SenderInfoModel {

    private long id;
    private String nickname;
    private String remark;
    private String memberName;
    private String specialTitle;
    private String permission;
    private long joinTimestamp;
    private long lastSpeakTimestamp;
    private long muteTimeRemaining;
    private GroupInfoModel group;

}
