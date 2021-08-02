package me.sharuru.srrbot.business.event.model;

import lombok.Data;

@Data
public class GroupMessageResponseModel {

    private int code;
    private String msg;
    private Long messageId;

}
