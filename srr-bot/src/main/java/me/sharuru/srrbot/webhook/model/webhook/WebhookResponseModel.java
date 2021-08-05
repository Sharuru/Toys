package me.sharuru.srrbot.webhook.model.webhook;

import lombok.Data;
import me.sharuru.srrbot.webhook.model.command.CommandModel;

@Data
public class WebhookResponseModel<T extends CommandModel> {

    private String command;
    private T content;

}
