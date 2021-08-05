package me.sharuru.srrbot.webhook;

import lombok.extern.slf4j.Slf4j;
import me.sharuru.srrbot.common.BotConstants;
import me.sharuru.srrbot.webhook.model.command.CommandModel;
import me.sharuru.srrbot.webhook.model.webhook.WebhookRequestModel;
import me.sharuru.srrbot.webhook.model.webhook.WebhookResponseModel;
import me.sharuru.srrbot.webhook.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequestMapping("/webhook")
public class WebhookEndpoint {

    @Autowired
    MessageService messageService;

    @Value("${srrBot.targetQQGroup}")
    private Long targetQQGroup;


    @ResponseBody
    @RequestMapping
    public WebhookResponseModel<? extends CommandModel> webhook(@RequestBody WebhookRequestModel requestModel) {

        if (BotConstants.EVENT_TYPE_GROUP_MESSAGE.equals(requestModel.getType())
                && targetQQGroup == requestModel.getSender().getGroup().getId()
                && BotConstants.MSG_TYPE_PLAIN.equals(requestModel.getBaseMessageChain().getType())) {
            log.info("Target: {}", requestModel);
            return messageService.messageHandler(requestModel);
        }

        return new WebhookResponseModel<>();
    }
}
