package me.sharuru.srrbot.webhook;

import lombok.extern.slf4j.Slf4j;
import me.sharuru.srrbot.common.BotConstants;
import me.sharuru.srrbot.common.BotUtils;
import me.sharuru.srrbot.common.InMemoryQuotaLimiter;
import me.sharuru.srrbot.entity.MaterialEntity;
import me.sharuru.srrbot.entity.UserEntity;
import me.sharuru.srrbot.mapper.UserMapper;
import me.sharuru.srrbot.webhook.model.command.SendGroupMessageModel;
import me.sharuru.srrbot.webhook.model.webhook.MessageChainInfoModel;
import me.sharuru.srrbot.webhook.model.webhook.WebhookRequestModel;
import me.sharuru.srrbot.webhook.model.webhook.WebhookResponseModel;
import me.sharuru.srrbot.webhook.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
public class MessageRouter {

    @Value("${srrBot.masterQQ}")
    private String masterQQ;

    @Autowired
    private BotUtils botUtils;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FeedService feedService;

    @Autowired
    private InMemoryQuotaLimiter quotaLimiter;

    public WebhookResponseModel<SendGroupMessageModel> dispatcher(WebhookRequestModel requestModel) {

        String msgText = requestModel.getBaseMessageChain().getText().replaceAll("\\s+", "").toLowerCase();
        String senderNumber = String.valueOf(requestModel.getSender().getId());

        switch (msgText) {
            case "投喂如如":
            case "投喂ruru": {
                if (!quotaLimiter.isLimited(senderNumber, BotConstants.COMM_FEED_RURU)) {
                    return this.generalResponse(BotConstants.COMM_FEED_RURU, requestModel);
                } else {
                    return this.generalResponse(BotConstants.COMM_OVER_QUOTA, requestModel);
                }
            }
            case "抢如如投喂":
            case "抢ruru投喂": {
                if (!quotaLimiter.isLimited(senderNumber, BotConstants.COMM_ROB_RURU)) {
                    return this.generalResponse(BotConstants.COMM_ROB_RURU, requestModel);
                } else {
                    return this.generalResponse(BotConstants.COMM_OVER_QUOTA, requestModel);
                }
            }
            case "恰ruru":
            case "恰如如": {
                if (!quotaLimiter.isLimited(senderNumber, BotConstants.COMM_EAT_RURU)) {
                    return this.generalResponse(BotConstants.COMM_EAT_RURU, requestModel);
                } else {
                    return this.generalResponse(BotConstants.COMM_OVER_QUOTA, requestModel);
                }
            }
            case "ruaruru":
            case "rua如如": {
                if (!quotaLimiter.isLimited(senderNumber, BotConstants.COMM_RUA_RURU)) {
                    return this.generalResponse(BotConstants.COMM_RUA_RURU, requestModel);
                } else {
                    return this.generalResponse(BotConstants.COMM_OVER_QUOTA, requestModel);
                }
            }
            case "晚安":
            case "晚安。": {
                if (!quotaLimiter.isLimited(senderNumber, BotConstants.COMM_NIGHT_RURU)) {
                    return this.generalResponse(BotConstants.COMM_NIGHT_RURU, requestModel);
                } else {
                    return this.generalResponse(BotConstants.COMM_OVER_QUOTA, requestModel);
                }
            }
            case "投喂f5": {
                if (!quotaLimiter.isLimited(senderNumber, BotConstants.COMM_FEED_LELE)) {
                    return this.generalResponse(BotConstants.COMM_FEED_LELE, requestModel);
                } else {
                    return this.generalResponse(BotConstants.COMM_OVER_QUOTA, requestModel);
                }
            }
            case "抢f5投喂": {
                if (!quotaLimiter.isLimited(senderNumber, BotConstants.COMM_ROB_LELE)) {
                    return this.generalResponse(BotConstants.COMM_ROB_LELE, requestModel);
                } else {
                    return this.generalResponse(BotConstants.COMM_OVER_QUOTA, requestModel);
                }
            }
            case "如如猫猫":
            case "ruru猫猫":
            case "猫猫如如":
            case "猫猫ruru": {
                return this.generalResponse(BotConstants.COMM_MAO_RURU, requestModel);
            }
            case "来张煌图":
            case "补充喵喵能量": {
                if (!quotaLimiter.isLimited(senderNumber, BotConstants.COMM_BLAZE_POWER)) {
                    return this.generalResponse(BotConstants.COMM_BLAZE_POWER, requestModel);
                } else {
                    return this.generalResponse(BotConstants.COMM_OVER_QUOTA, requestModel);
                }
            }
            case "如如养成":
            case "ruru养成":
            case "养成如如":
            case "养成ruru": {
                if (!quotaLimiter.isLimited(senderNumber, BotConstants.COMM_RURU_INFO, 5)) {
                    return feedService.getFeedInfo(requestModel);
                } else {
                    return this.generalResponse(BotConstants.COMM_OVER_QUOTA, requestModel);
                }
            }
            case "透如如":
            case "透ruru": {
                if (!quotaLimiter.isLimited(senderNumber, BotConstants.COMM_GACHA_RURU, 10)) {
                    return feedService.gacha(requestModel);
                } else {
                    return this.generalResponse(BotConstants.COMM_OVER_QUOTA, requestModel);
                }
            }
            default:
                return null;
        }
    }

    private WebhookResponseModel<SendGroupMessageModel> generalResponse(String catalog, WebhookRequestModel requestModel) {
        WebhookResponseModel<SendGroupMessageModel> responseModel = new WebhookResponseModel<>();
        responseModel.setCommand(BotConstants.COMMAND_SEND_GROUP_MESSAGE);

        SendGroupMessageModel groupMessageModel = new SendGroupMessageModel();
        groupMessageModel.setTarget(requestModel.getSender().getGroup().getId());
        MessageChainInfoModel messagePayload = new MessageChainInfoModel();

        String userNumber = String.valueOf(requestModel.getSender().getId());
        UserEntity userInfo = userMapper.findOneByNumber(userNumber);
        int materialThreshold = 0;

        if (userInfo != null) {
            materialThreshold = userInfo.getScore();
        } else {
            userInfo = new UserEntity();
            userInfo.setNumber(userNumber);
            userInfo.setScore(0);
            userInfo.setPotential(1);
        }

        MaterialEntity selectedMaterial = botUtils.getRandomSelectedMaterial(catalog, materialThreshold);

        // 满信赖后基于潜能加值
        String additionalContext = "";
        if (userInfo.getScore() > 200 && userInfo.getPotential() > 1 && !BotConstants.COMM_OVER_QUOTA.equals(catalog)) {
            int probability = ThreadLocalRandom.current().nextInt(1, 101);
            // 20%~25% 概率获得加值
            if (probability + userInfo.getPotential() <= 25) {
                // 基础加值 * 潜能等级
                MaterialEntity additionalMaterial = botUtils.getRandomSelectedMaterial(BotConstants.COMM_ADDITION_SCORE, 0);
                int additionalScore = additionalMaterial.getScore() * userInfo.getPotential();
                userInfo.setScore(userInfo.getScore() + selectedMaterial.getScore() + additionalScore);
                additionalContext = botUtils.fillNickname(additionalMaterial.getContext(), requestModel.getSender().getMemberName());
                additionalContext += "\n" + "（额外增加了" + additionalScore + "点经验）";
            }
        } else {
            userInfo.setScore(userInfo.getScore() + selectedMaterial.getScore());
        }

        userMapper.upsertUserInfo(userInfo.getNumber(), userInfo.getScore());

        if (BotConstants.MSG_TYPE_PLAIN.equals(selectedMaterial.getType())) {
            messagePayload.setType(BotConstants.MSG_TYPE_PLAIN);
            String selectedContextText = botUtils.fillNickname(selectedMaterial.getContext(), requestModel.getSender().getMemberName());
            String contextText = StringUtils.hasText(additionalContext) ? selectedContextText + "\n" + additionalContext : selectedContextText;
            messagePayload.setText(contextText);
        } else if (BotConstants.MSG_TYPE_IMAGE.equals(selectedMaterial.getType())) {
            messagePayload.setType(BotConstants.MSG_TYPE_IMAGE);
            messagePayload.setUrl(selectedMaterial.getContext());
        }

        groupMessageModel.getMessageChain().add(messagePayload);

        // 主人自己发送消息时加入署名方便区分
        if (masterQQ.equals(userNumber) && !BotConstants.COMM_BLAZE_POWER.equals(catalog)) {
            MessageChainInfoModel additionalPayload = new MessageChainInfoModel();
            additionalPayload.setType(BotConstants.MSG_TYPE_PLAIN);
            additionalPayload.setText("【自动如如】");
            groupMessageModel.getMessageChain().add(additionalPayload);
        }

        responseModel.setContent(groupMessageModel);

        return responseModel;
    }


}
