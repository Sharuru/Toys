package self.srr.bot.biz.chat.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import self.srr.bot.base.common.BotContrast;
import self.srr.bot.base.common.BotRequestModel;
import self.srr.bot.base.common.BotResponseModel;
import self.srr.bot.base.common.BotUtils;

/**
 * Chat router
 * <p>
 * Created by Sharuru on 2017/06/01.
 */
@RestController
@RequestMapping("/api/chat")
@Slf4j
public class ChatController {

    /**
     * Just reply what I said
     *
     * @param botRequest request
     * @return response
     */
    @RequestMapping(value = "")
    public BotResponseModel replyBack(BotRequestModel botRequest) {
        BotResponseModel responseModel = BotUtils.getDefaultResponseModel();

        if (BotUtils.isUserMaster(botRequest.getUser_id())) {
            responseModel.setText(botRequest.getText());
            responseModel.setResponse_type(BotContrast.BOT_RESP_TYPE_ICH);
        }

        return responseModel;
    }
}
