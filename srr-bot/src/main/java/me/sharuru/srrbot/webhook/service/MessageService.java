package me.sharuru.srrbot.webhook.service;

import lombok.extern.slf4j.Slf4j;
import me.sharuru.srrbot.webhook.model.command.SendGroupMessageModel;
import me.sharuru.srrbot.webhook.model.webhook.WebhookRequestModel;
import me.sharuru.srrbot.webhook.model.webhook.WebhookResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MessageService {

    @Autowired
    RuruService ruruService;

    @Autowired
    LeleService leleService;


    public WebhookResponseModel<SendGroupMessageModel> messageHandler(WebhookRequestModel webhookRequestModel) {

        String msgText = webhookRequestModel.getBaseMessageChain().getText().replaceAll("\\s+", "").toLowerCase();

        switch (msgText) {
            case "投喂如如":
            case "投喂ruru": {
                return ruruService.feedRuru();
            }
            case "抢如如投喂":
            case "抢ruru投喂": {
                return ruruService.robRuru();
            }
            case "恰ruru":
            case "恰如如": {
                return ruruService.eatRuru();
            }
            case "ruaruru":
            case "rua如如": {
                return ruruService.ruaRuru();
            }
            case "晚安":
            case "晚安。": {
                return ruruService.nightRuru();
            }
            case "投喂F5" :{
                return leleService.feedLele();
            }
            case "抢F5投喂" :{
                return leleService.robLele();
            }
            default:
                return null;
        }
    }




}
