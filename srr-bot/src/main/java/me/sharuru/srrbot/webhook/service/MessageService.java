package me.sharuru.srrbot.webhook.service;

import lombok.extern.slf4j.Slf4j;
import me.sharuru.srrbot.common.BotConstants;

import me.sharuru.srrbot.webhook.model.command.SendGroupMessageModel;
import me.sharuru.srrbot.webhook.model.webhook.MessageChainInfoModel;
import me.sharuru.srrbot.webhook.model.webhook.WebhookRequestModel;
import me.sharuru.srrbot.webhook.model.webhook.WebhookResponseModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
public class MessageService {

    @Value("${srrBot.targetQQGroup}")
    private Long targetQQGroup;

    public WebhookResponseModel<SendGroupMessageModel> messageHandler(WebhookRequestModel webhookRequestModel) {

        String msgText = webhookRequestModel.getBaseMessageChain().getText().replaceAll("\\s+", "").toLowerCase();

        switch (msgText) {
            case "投喂如如":
            case "投喂ruru": {
                return feedRuru();
            }
            case "抢如如投喂":
            case "抢ruru投喂": {
                return robRuru();
            }
            case "恰ruru":
            case "恰如如": {
                return eatRuru();
            }
            case "ruaruru":
            case "rua如如": {
                return ruaRuru();
            }
            case "晚安":
            case "晚安。": {
                return nightRuru();
            }
            default:
                return null;
        }
    }

    private WebhookResponseModel<SendGroupMessageModel> feedRuru() {

        WebhookResponseModel<SendGroupMessageModel> responseModel = new WebhookResponseModel<>();
        responseModel.setCommand(BotConstants.COMMAND_SEND_GROUP_MESSAGE);

        SendGroupMessageModel groupMessageModel = new SendGroupMessageModel();
        groupMessageModel.setTarget(targetQQGroup);
        MessageChainInfoModel messagePayload = new MessageChainInfoModel();

        List<String> responseMsgList = Arrays.asList(
                "感谢投喂，mua~",
                "唔…我吃不下啦！",
                "嗯？谢谢投喂。",
                "如果哪天如如成为了 debu，其中 99.99% 都是你的错啦！ε=ε=ε=(~￣▽￣)~",
                "哼，挖路酷奈衣。",
                "唔姆唔姆，谢谢！",
                "mua~",
                "啊噗噜派~！"
        );

        messagePayload.setType(BotConstants.MSG_TYPE_PLAIN);
        messagePayload.setText(responseMsgList.get(new Random().nextInt(responseMsgList.size())));
        groupMessageModel.getMessageChain().add(messagePayload);

        responseModel.setContent(groupMessageModel);

        return responseModel;
    }

    private WebhookResponseModel<SendGroupMessageModel> robRuru() {

        WebhookResponseModel<SendGroupMessageModel> responseModel = new WebhookResponseModel<>();
        responseModel.setCommand(BotConstants.COMMAND_SEND_GROUP_MESSAGE);

        SendGroupMessageModel groupMessageModel = new SendGroupMessageModel();
        groupMessageModel.setTarget(targetQQGroup);
        MessageChainInfoModel messagePayload = new MessageChainInfoModel();

        List<String> responseMsgList = Arrays.asList(
                "呜呜呜，为什么要做这样的事情……",
                "QAQ",
                "这种水平的抢夺，还请允许我保留体力。",
                "没事，被抢掉的投喂从阿比那里再抢过来就好啦！（屑）",
                "如如开始变得有点讨厌尼惹 QwQ",
                "我数到三，你们还有投降的机会，三~",
                "没事，反正如如也不喜欢这个……大概……（泣）",
                "坏东西，坏东西！",
                "嗯......我，我会忍住不搞折你的手......",
                "呵......",
                ".rd 这合适吗？让我们来问问阿比吧！"
        );

        messagePayload.setType(BotConstants.MSG_TYPE_PLAIN);
        messagePayload.setText(responseMsgList.get(new Random().nextInt(responseMsgList.size())));
        groupMessageModel.getMessageChain().add(messagePayload);

        responseModel.setContent(groupMessageModel);

        return responseModel;
    }

    private WebhookResponseModel<SendGroupMessageModel> eatRuru() {

        WebhookResponseModel<SendGroupMessageModel> responseModel = new WebhookResponseModel<>();
        responseModel.setCommand(BotConstants.COMMAND_SEND_GROUP_MESSAGE);

        SendGroupMessageModel groupMessageModel = new SendGroupMessageModel();
        groupMessageModel.setTarget(targetQQGroup);
        MessageChainInfoModel messagePayload = new MessageChainInfoModel();

        List<String> responseMsgList = Arrays.asList(
                "不准恰，不准恰，我不好恰！",
                "去恰阿比，阿比好恰！",
                "我还没熟……（安详）",
                "如果……只是恰一口的话……（伸出手臂）",
                "说好了，只能恰一口哦！（撅屁股——）",
                "好怪哦……",
                "有点奇怪，不过，也不算讨厌……",
                "嘣恰恰嘣恰恰嘣恰恰~是要和如如一起跳舞吗？",
                "想恰如如的话，应该也做好了被如如恰的觉悟了吧？（咬）"
        );

        messagePayload.setType(BotConstants.MSG_TYPE_PLAIN);
        messagePayload.setText(responseMsgList.get(new Random().nextInt(responseMsgList.size())));
        groupMessageModel.getMessageChain().add(messagePayload);

        responseModel.setContent(groupMessageModel);

        return responseModel;
    }

    private WebhookResponseModel<SendGroupMessageModel> ruaRuru() {

        WebhookResponseModel<SendGroupMessageModel> responseModel = new WebhookResponseModel<>();
        responseModel.setCommand(BotConstants.COMMAND_SEND_GROUP_MESSAGE);

        SendGroupMessageModel groupMessageModel = new SendGroupMessageModel();
        groupMessageModel.setTarget(targetQQGroup);
        MessageChainInfoModel messagePayload = new MessageChainInfoModel();

        List<String> responseMsgList = Arrays.asList(
                "rua！",
                "不准 rua！人家刚顺好的毛 QwQ",
                "很舒服呢，谢谢你！",
                "忒嘿~ (●'◡'●)",
                "我喜欢这种感觉！",
                "至……福…… >////<",
                "mofumofu，谢谢你！",
                "不，不要碰我！......你会受伤的。",
                "唔啊......好困......干脆一起打个盹吧......",
                "为了能让你 rua 的舒服一点，如如我天天都有在好好的做着护理哦~",
                "（丢人的声音）"
        );

        messagePayload.setType(BotConstants.MSG_TYPE_PLAIN);
        messagePayload.setText(responseMsgList.get(new Random().nextInt(responseMsgList.size())));
        groupMessageModel.getMessageChain().add(messagePayload);

        responseModel.setContent(groupMessageModel);

        return responseModel;
    }

    private WebhookResponseModel<SendGroupMessageModel> nightRuru() {

        WebhookResponseModel<SendGroupMessageModel> responseModel = new WebhookResponseModel<>();
        responseModel.setCommand(BotConstants.COMMAND_SEND_GROUP_MESSAGE);

        SendGroupMessageModel groupMessageModel = new SendGroupMessageModel();
        groupMessageModel.setTarget(targetQQGroup);
        MessageChainInfoModel messagePayload = new MessageChainInfoModel();

        messagePayload.setType(BotConstants.MSG_TYPE_PLAIN);
        messagePayload.setText("仰望星辉\n如银之诗篇一般的感情\n沉默将声音隐藏住 在无色之中溶解了\n最后你也化为虚幻\n请于星辉海水下长眠.....至少现在");


        groupMessageModel.getMessageChain().add(messagePayload);

        responseModel.setContent(groupMessageModel);

        return responseModel;
    }


}
