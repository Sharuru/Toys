package me.sharuru.srrbot.webhook.service;

import me.sharuru.srrbot.common.BotConstants;
import me.sharuru.srrbot.webhook.model.command.SendGroupMessageModel;
import me.sharuru.srrbot.webhook.model.webhook.MessageChainInfoModel;
import me.sharuru.srrbot.webhook.model.webhook.WebhookResponseModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class LeleService {

    @Value("${srrBot.targetQQGroup}")
    private Long targetQQGroup;

    public WebhookResponseModel<SendGroupMessageModel> feedLele() {

        WebhookResponseModel<SendGroupMessageModel> responseModel = new WebhookResponseModel<>();
        responseModel.setCommand(BotConstants.COMMAND_SEND_GROUP_MESSAGE);

        SendGroupMessageModel groupMessageModel = new SendGroupMessageModel();
        groupMessageModel.setTarget(targetQQGroup);
        MessageChainInfoModel messagePayload = new MessageChainInfoModel();

        List<String> responseMsgList = Arrays.asList(
                "要投喂的是 F5……而不是可爱的如如吗……？",
                "摸摸，摸摸，只要你投喂 F5，我们就是好朋友啦！",
                "有时候，会觉得……F5 真的是被大家爱着呢……",
                "谢谢投喂！……欸……原来不是投喂如如啊……（探头）"
        );

        messagePayload.setType(BotConstants.MSG_TYPE_PLAIN);
        messagePayload.setText(responseMsgList.get(new Random().nextInt(responseMsgList.size())));
        groupMessageModel.getMessageChain().add(messagePayload);

        responseModel.setContent(groupMessageModel);

        return responseModel;
    }

    public WebhookResponseModel<SendGroupMessageModel> robLele() {

        WebhookResponseModel<SendGroupMessageModel> responseModel = new WebhookResponseModel<>();
        responseModel.setCommand(BotConstants.COMMAND_SEND_GROUP_MESSAGE);

        SendGroupMessageModel groupMessageModel = new SendGroupMessageModel();
        groupMessageModel.setTarget(targetQQGroup);
        MessageChainInfoModel messagePayload = new MessageChainInfoModel();

        List<String> responseMsgList = Arrays.asList(
                "稍微欺负一下 F5，感觉也不错呢！",
                "不准抢如如的投喂！……嗯？你说，你抢的是 F5 的？（你请）",
                "高价回收 F5 投喂，价格优惠，可换不锈钢脸盆~",
                "真是可怕的行为……希望不要来抢如如的投喂……",
                "哟西哟西~这样，F5 就更加离不开如如了呢。嘿嘿……嘿嘿……"
        );

        messagePayload.setType(BotConstants.MSG_TYPE_PLAIN);
        messagePayload.setText(responseMsgList.get(new Random().nextInt(responseMsgList.size())));
        groupMessageModel.getMessageChain().add(messagePayload);

        responseModel.setContent(groupMessageModel);

        return responseModel;
    }

}
