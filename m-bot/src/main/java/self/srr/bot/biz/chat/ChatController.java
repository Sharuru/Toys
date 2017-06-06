package self.srr.chat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import self.srr.common.BotContrast;
import self.srr.common.BotRequestModel;
import self.srr.common.BotResponseModel;

/**
 * 聊天路由
 * <p>
 * Created by Sharuru on 2017/06/01.
 */
@RestController
@RequestMapping("/bot/chat")
@Slf4j
public class ChatController {

    @RequestMapping(value = "")
    public BotResponseModel chat(BotRequestModel botReq) {
        BotResponseModel respModel = new BotResponseModel();
        if (BotContrast.BOT_MASTER_ID.equalsIgnoreCase(botReq.getUser_id())) {
            respModel.setText(botReq.getText());
            respModel.setResponse_type(BotContrast.BOT_RES_TYPE_ICH);
        }
        return respModel;
    }
}
