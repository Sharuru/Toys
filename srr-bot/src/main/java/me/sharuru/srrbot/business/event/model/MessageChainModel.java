package me.sharuru.srrbot.business.event.model;

import lombok.Data;

@Data
public class MessageChainModel {

    private String type;
    private Long id;
    private String time;
    private String text;
    private String url;

}
