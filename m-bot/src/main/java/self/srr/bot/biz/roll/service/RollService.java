package self.srr.bot.biz.roll.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import self.srr.bot.base.common.BotRequestModel;
import self.srr.bot.base.common.BotResponseModel;
import self.srr.bot.base.common.BotUtils;
import self.srr.bot.biz.roll.repository.RollUserRepository;

/**
 * Roll service
 * <p>
 * Created by Sharuru on 2017/06/14.
 */
@Service
@Slf4j
public class RollService {

    @Autowired
    private RollUserRepository rollUserRepository;

    private BotRequestModel botRequestModel = new BotRequestModel();

    public BotResponseModel start(BotRequestModel botRequestModel) {
        this.botRequestModel = botRequestModel;

        //parse command
        String[] args = BotUtils.commandParser(botRequestModel.getText());
        String action = args[0];
        BotResponseModel botResponseModel = BotUtils.getDefaultResponseModel(args);

        log.info("User '" + botRequestModel.getUser_name() + "' inputted '" + botRequestModel.getText() + "'");

        // switch action
        if ("help".equalsIgnoreCase(action)) {
            botResponseModel = helpMsgBiz(botResponseModel);
        } else if ("card".equalsIgnoreCase(action)) {

        } else {

        }

        botResponseModel.appendAtWho(botRequestModel.getUser_name());

        return botResponseModel;
    }

    /**
     * Help message business
     *
     * @param botResponseModel prev. response
     * @return response
     */
    private BotResponseModel helpMsgBiz(BotResponseModel botResponseModel) {

        String text = "" +
                "Roll 点功能的帮助信息：\n" +
                "使用方法：输入 `/roll 指令。`\n" +
                "`/roll` 从 1-100 中随意 roll 出一种点数；\n" +
                "`/roll help` 显示本帮助信息；\n" +
                "`/roll card o` 进行一次单抽（3 水晶）；\n" +
                "`/roll card e` 进行十一连（30 水晶，十一连必出 UR）；\n" +
                "`/roll status` 查看个人帐户信息；\n" +
                "`/roll switch 30` 获取 30 水晶（180 元）；\n" +
                "在指令后追加 s 表示将本次响应公开；";

        botResponseModel.setText(text);

        return botResponseModel;
    }



}
