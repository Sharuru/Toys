package me.sharuru.srrbot.business.event.model;

import lombok.Data;

@Data
public class SenderModel {

    private Long id;
    private String memberName;
    private String permission;
    private GroupInfoModel group;

}