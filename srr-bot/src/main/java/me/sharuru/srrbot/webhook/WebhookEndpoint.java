package me.sharuru.srrbot.webhook;

import lombok.extern.slf4j.Slf4j;
import me.sharuru.srrbot.common.BotConstants;
import me.sharuru.srrbot.webhook.model.command.CommandModel;
import me.sharuru.srrbot.webhook.model.webhook.WebhookRequestModel;
import me.sharuru.srrbot.webhook.model.webhook.WebhookResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/webhook")
public class WebhookEndpoint {

    @Autowired
    MessageRouter messageRouter;

    @Value("${srrBot.targetQQGroups}")
    private List<String> targetQQGroups = new ArrayList<>(0);

    @ResponseBody
    @RequestMapping
    public WebhookResponseModel<? extends CommandModel> webhook(@RequestBody WebhookRequestModel requestModel) {

        if (BotConstants.EVENT_TYPE_GROUP_MESSAGE.equals(requestModel.getType())
                && targetQQGroups.contains(String.valueOf(requestModel.getSender().getGroup().getId()))
                && BotConstants.MSG_TYPE_PLAIN.equals(requestModel.getBaseMessageChain().getType())) {
            log.info("Target: {}", requestModel);
            return messageRouter.dispatcher(requestModel);
        }

        return new WebhookResponseModel<>();
    }
}
