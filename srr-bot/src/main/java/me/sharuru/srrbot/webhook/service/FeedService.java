package me.sharuru.srrbot.webhook.service;

import lombok.extern.slf4j.Slf4j;
import me.sharuru.srrbot.common.BotConstants;
import me.sharuru.srrbot.common.BotUtils;
import me.sharuru.srrbot.entity.MaterialEntity;
import me.sharuru.srrbot.entity.UserEntity;
import me.sharuru.srrbot.mapper.UserMapper;
import me.sharuru.srrbot.webhook.model.command.SendGroupMessageModel;
import me.sharuru.srrbot.webhook.model.webhook.MessageChainInfoModel;
import me.sharuru.srrbot.webhook.model.webhook.WebhookRequestModel;
import me.sharuru.srrbot.webhook.model.webhook.WebhookResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
public class FeedService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BotUtils botUtils;

    public WebhookResponseModel<SendGroupMessageModel> getFeedInfo(WebhookRequestModel requestModel) {
        WebhookResponseModel<SendGroupMessageModel> responseModel = new WebhookResponseModel<>();
        responseModel.setCommand(BotConstants.COMMAND_SEND_GROUP_MESSAGE);

        SendGroupMessageModel groupMessageModel = new SendGroupMessageModel();
        groupMessageModel.setTarget(requestModel.getSender().getGroup().getId());
        MessageChainInfoModel messagePayload = new MessageChainInfoModel();

        String userNumber = String.valueOf(requestModel.getSender().getId());
        UserEntity userInfo = userMapper.findOneByNumber(userNumber);

        String msgText = "你好，" + requestModel.getSender().getMemberName() + "。\n";
        msgText += "如如当前的状态为：\n" +
                "【信赖值】";
        String trust = "0%";
        String ratingText = "让我们好好搞好关系吧！";

        if (userInfo != null) {
            // 满信赖
            if (userInfo.getScore() > 200) {
                int eliteLevel = 1;
                // 等级计算（难度系数）
                int level = (userInfo.getScore() - 200) / 35;
                // 精一升精二
                if (level >= 80) {
                    eliteLevel = 2;
                    level = level - 80 + 1;
                    // 精二满级
                    if (level >= 90) {
                        level = 90;
                    }
                }
                msgText += "200%\n";
                msgText += "【等级】" + (level == 0 ? 1 : level) + "\n" +
                        "【精英阶段】" + botUtils.convertToRomanNumerals(eliteLevel) + "\n" +
                        "【潜能】" + botUtils.convertToRomanNumerals(userInfo.getPotential()) + "\n";
                MaterialEntity additionalMaterial = botUtils.getRandomSelectedMaterial(BotConstants.COMM_ADDITION_SCORE, 0);
                ratingText = botUtils.fillNickname(additionalMaterial.getContext(), requestModel.getSender().getMemberName());
                msgText += ratingText;
            } else {
                if (userInfo.getScore() > 0 && userInfo.getScore() < 50) {
                    trust = userInfo.getScore() + "%";
                    ratingText = "呜姆呜姆，如如大概有点懂了！";
                } else if (userInfo.getScore() >= 50 && userInfo.getScore() < 100) {
                    trust = userInfo.getScore() + "%";
                    ratingText = "如如觉得你值得信赖。";
                } else if (userInfo.getScore() >= 100 && userInfo.getScore() < 150) {
                    trust = userInfo.getScore() + "%";
                    ratingText = "昨天晚上，如如好像梦到你了哦~";
                } else if (userInfo.getScore() >= 150 && userInfo.getScore() < 200) {
                    trust = userInfo.getScore() + "%";
                    ratingText = "如如……有点话……想和你说……";
                } else if (userInfo.getScore() >= 200) {
                    trust = "200%";
                    ratingText = "如如喜欢你哦~！啾！";
                }
                msgText += trust + "\n";
                msgText += ratingText;
            }
        }

        messagePayload.setType(BotConstants.MSG_TYPE_PLAIN);
        messagePayload.setText(msgText);
        groupMessageModel.getMessageChain().add(messagePayload);

        responseModel.setContent(groupMessageModel);

        return responseModel;
    }

    public WebhookResponseModel<SendGroupMessageModel> gacha(WebhookRequestModel requestModel){
        WebhookResponseModel<SendGroupMessageModel> responseModel = new WebhookResponseModel<>();
        responseModel.setCommand(BotConstants.COMMAND_SEND_GROUP_MESSAGE);

        SendGroupMessageModel groupMessageModel = new SendGroupMessageModel();
        groupMessageModel.setTarget(requestModel.getSender().getGroup().getId());
        MessageChainInfoModel messagePayload = new MessageChainInfoModel();

        String userNumber = String.valueOf(requestModel.getSender().getId());
        UserEntity userInfo = userMapper.findOneByNumber(userNumber);

        if(userInfo != null){
            if(userInfo.getPotential() < 6){
                int probability = ThreadLocalRandom.current().nextInt(1, 101);
                if (probability + userInfo.getPotential() <= 7 - userInfo.getPotential()) {
                    MaterialEntity selectedMaterial = botUtils.getRandomSelectedMaterial(BotConstants.COMM_GACHA_RURU, 0);
                    String selectedContext = botUtils.fillNickname(selectedMaterial.getContext(), requestModel.getSender().getMemberName());
                    messagePayload.setType(selectedMaterial.getType());
                    messagePayload.setText(selectedContext);
                    userMapper.updateUserPotential(userNumber, userInfo.getPotential() + 1);
                } else {
                    MaterialEntity selectedMaterial = botUtils.getRandomSelectedMaterial(BotConstants.COMM_GACHA_RURU_X, 0);
                    String selectedContext = botUtils.fillNickname(selectedMaterial.getContext(), requestModel.getSender().getMemberName());
                    messagePayload.setType(selectedMaterial.getType());
                    messagePayload.setText(selectedContext);
                }
            } else {
                messagePayload.setType(BotConstants.MSG_TYPE_PLAIN);
                messagePayload.setText("已经达到最大潜能等级。");
            }
        } else {
            messagePayload.setType(BotConstants.MSG_TYPE_PLAIN);
            messagePayload.setText("如如好像好不太认识你的样子……");
        }

        groupMessageModel.getMessageChain().add(messagePayload);
        responseModel.setContent(groupMessageModel);

        return responseModel;

    }
}
