package me.sharuru.srrbot.webhook.service;

import lombok.extern.slf4j.Slf4j;
import me.sharuru.srrbot.common.BotConstants;
import me.sharuru.srrbot.entity.MaterialEntity;
import me.sharuru.srrbot.entity.UserEntity;
import me.sharuru.srrbot.mapper.MaterialMapper;
import me.sharuru.srrbot.mapper.UserMapper;
import me.sharuru.srrbot.webhook.model.command.SendGroupMessageModel;
import me.sharuru.srrbot.webhook.model.webhook.MessageChainInfoModel;
import me.sharuru.srrbot.webhook.model.webhook.WebhookRequestModel;
import me.sharuru.srrbot.webhook.model.webhook.WebhookResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Slf4j
@Service
public class MessageService {

    @Autowired
    private MaterialMapper materialMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FeedService feedService;

    @Autowired
    private InMemoryInteractiveQuotaLimiter quotaLimiter;

    public WebhookResponseModel<SendGroupMessageModel> messageHandler(WebhookRequestModel requestModel) {

        String msgText = requestModel.getBaseMessageChain().getText().replaceAll("\\s+", "").toLowerCase();
        String senderNumber = String.valueOf(requestModel.getSender().getId());

        switch (msgText) {
            case "投喂如如":
            case "投喂ruru": {
                if (!quotaLimiter.isInteractiveLimited(senderNumber, BotConstants.COMM_FEED_RURU)) {
                    return this.generalResponse(BotConstants.COMM_FEED_RURU, requestModel);
                } else {
                    return this.generalResponse(BotConstants.COMM_OVER_QUOTA, requestModel);
                }

            }
            case "抢如如投喂":
            case "抢ruru投喂": {
                if (!quotaLimiter.isInteractiveLimited(senderNumber, BotConstants.COMM_ROB_RURU)) {
                    return this.generalResponse(BotConstants.COMM_ROB_RURU, requestModel);
                } else {
                    return this.generalResponse(BotConstants.COMM_OVER_QUOTA, requestModel);
                }

            }
            case "恰ruru":
            case "恰如如": {
                if (!quotaLimiter.isInteractiveLimited(senderNumber, BotConstants.COMM_EAT_RURU)) {
                    return this.generalResponse(BotConstants.COMM_EAT_RURU, requestModel);
                } else {
                    return this.generalResponse(BotConstants.COMM_OVER_QUOTA, requestModel);
                }

            }
            case "ruaruru":
            case "rua如如": {
                if (!quotaLimiter.isInteractiveLimited(senderNumber, BotConstants.COMM_RUA_RURU)) {
                    return this.generalResponse(BotConstants.COMM_RUA_RURU, requestModel);
                } else {
                    return this.generalResponse(BotConstants.COMM_OVER_QUOTA, requestModel);
                }

            }
            case "晚安":
            case "晚安。": {
                if (!quotaLimiter.isInteractiveLimited(senderNumber, BotConstants.COMM_NIGHT_RURU)) {
                    return this.generalResponse(BotConstants.COMM_NIGHT_RURU, requestModel);
                } else {
                    return this.generalResponse(BotConstants.COMM_OVER_QUOTA, requestModel);
                }

            }
            case "投喂f5": {
                if (!quotaLimiter.isInteractiveLimited(senderNumber, BotConstants.COMM_FEED_LELE)) {
                    return this.generalResponse(BotConstants.COMM_FEED_LELE, requestModel);
                } else {
                    return this.generalResponse(BotConstants.COMM_OVER_QUOTA, requestModel);
                }
            }
            case "抢f5投喂": {
                if (!quotaLimiter.isInteractiveLimited(senderNumber, BotConstants.COMM_ROB_LELE)) {
                    return this.generalResponse(BotConstants.COMM_ROB_LELE, requestModel);
                } else {
                    return this.generalResponse(BotConstants.COMM_OVER_QUOTA, requestModel);
                }
            }
            case "如如养成":
            case "ruru养成":
            case "养成如如":
            case "养成ruru": {
                return feedService.feedInfo(requestModel);
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
        }

        List<MaterialEntity> materials = materialMapper.findAllByCatalogAndThreshold(catalog, materialThreshold);
        MaterialEntity selectedMaterial = materials.get(new Random().nextInt(materials.size()));
        userInfo.setScore(userInfo.getScore() + selectedMaterial.getScore());

        userMapper.upsertUserInfo(userInfo.getNumber(), userInfo.getScore());

        messagePayload.setType(BotConstants.MSG_TYPE_PLAIN);
        messagePayload.setText(selectedMaterial.getContext());
        groupMessageModel.getMessageChain().add(messagePayload);

        responseModel.setContent(groupMessageModel);

        return responseModel;
    }

}
