package me.sharuru.srrbot.webhook.model.command;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.sharuru.srrbot.webhook.model.webhook.MessageChainInfoModel;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class SendGroupMessageModel extends CommandModel {

    private long target;
    private List<MessageChainInfoModel> messageChain = new ArrayList<>(0);

}
