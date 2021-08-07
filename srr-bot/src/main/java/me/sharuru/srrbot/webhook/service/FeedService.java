package me.sharuru.srrbot.webhook.service;

import lombok.extern.slf4j.Slf4j;
import me.sharuru.srrbot.common.BotConstants;
import me.sharuru.srrbot.entity.UserEntity;
import me.sharuru.srrbot.mapper.UserMapper;
import me.sharuru.srrbot.webhook.model.command.SendGroupMessageModel;
import me.sharuru.srrbot.webhook.model.webhook.MessageChainInfoModel;
import me.sharuru.srrbot.webhook.model.webhook.WebhookRequestModel;
import me.sharuru.srrbot.webhook.model.webhook.WebhookResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
public class FeedService {

    @Autowired
    private UserMapper userMapper;

    public WebhookResponseModel<SendGroupMessageModel> feedInfo(WebhookRequestModel requestModel) {
        WebhookResponseModel<SendGroupMessageModel> responseModel = new WebhookResponseModel<>();
        responseModel.setCommand(BotConstants.COMMAND_SEND_GROUP_MESSAGE);

        SendGroupMessageModel groupMessageModel = new SendGroupMessageModel();
        groupMessageModel.setTarget(requestModel.getSender().getGroup().getId());
        MessageChainInfoModel messagePayload = new MessageChainInfoModel();

        String userNumber = String.valueOf(requestModel.getSender().getId());
        UserEntity userInfo = userMapper.findOneByNumber(userNumber);

        String nickname = StringUtils.hasLength(requestModel.getSender().getMemberName()) ? requestModel.getSender().getMemberName() : "名字怪怪的群友哟";
        String msgText = "你好，" + nickname + "。\n";
        msgText += "如如当前对你的信赖值为：";
        String trust = "0%";
        String rating = "让我们好好搞好关系吧！";

        if (userInfo != null) {
            if (userInfo.getScore() > 0 && userInfo.getScore() < 50) {
                trust = userInfo.getScore() + "%";
                rating = "呜姆呜姆，如如大概有点懂了！";
            } else if (userInfo.getScore() >= 50 && userInfo.getScore() < 100) {
                trust = userInfo.getScore() + "%";
                rating = "如如觉得你值得信赖。";
            } else if (userInfo.getScore() >= 100 && userInfo.getScore() < 150) {
                trust = userInfo.getScore() + "%";
                rating = "昨天晚上，如如好像梦到你了哦~";
            } else if (userInfo.getScore() >= 150 && userInfo.getScore() < 200) {
                trust = userInfo.getScore() + "%";
                rating = "如如……有点话……想和你说……";
            } else if (userInfo.getScore() >= 200) {
                trust = "200%";
                rating = "如如喜欢你哦~！啾！";
            }
        }

        msgText += trust + "\n";
        msgText += rating;

        messagePayload.setType(BotConstants.MSG_TYPE_PLAIN);
        messagePayload.setText(msgText);
        groupMessageModel.getMessageChain().add(messagePayload);

        responseModel.setContent(groupMessageModel);

        return responseModel;
    }

}
