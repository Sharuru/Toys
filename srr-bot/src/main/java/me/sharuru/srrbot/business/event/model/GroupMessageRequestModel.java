package me.sharuru.srrbot.business.event.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GroupMessageRequestModel {

    private String sessionKey;
    private Long target;
    private List<MessageChainModel> messageChain = new ArrayList<>(0);

}
