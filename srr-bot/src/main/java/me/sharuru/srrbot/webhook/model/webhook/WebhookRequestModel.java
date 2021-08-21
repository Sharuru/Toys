package me.sharuru.srrbot.webhook.model.webhook;

import lombok.Data;
import me.sharuru.srrbot.common.BotConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Webhook基础对象
 */
@Data
public class WebhookRequestModel {
    private String type;
    private SenderInfoModel sender;
    private List<MessageChainInfoModel> messageChain = new ArrayList<>(0);

    public MessageChainInfoModel getBaseMessageChain() {
        return this.messageChain.stream().filter(msg -> !BotConstants.MSG_TYPE_SOURCE.equals(msg.getType())).findFirst().orElse(new MessageChainInfoModel());
    }
}
