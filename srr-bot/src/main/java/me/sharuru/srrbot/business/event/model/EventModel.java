package me.sharuru.srrbot.business.event.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EventModel {

    private String type;
    private List<MessageChainModel> messageChain = new ArrayList<>(0);
    private SenderModel sender;

}
