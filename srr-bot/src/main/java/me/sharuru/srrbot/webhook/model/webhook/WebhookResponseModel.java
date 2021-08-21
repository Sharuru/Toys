package me.sharuru.srrbot.webhook.model.webhook;

import lombok.Data;
import me.sharuru.srrbot.webhook.model.command.CommandModel;

/**
 * Webhook响应基础对象
 */
@Data
public class WebhookResponseModel<T extends CommandModel> {

    private String command;
    private T content;

}
