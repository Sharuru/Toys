package self.srr.fish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import self.srr.common.BotRequestModel;
import self.srr.common.BotResponseModel;

/**
 * 摸鱼计时路由
 * <p>
 * Created by Sharuru on 2017/04/28.
 */
@RestController
@RequestMapping("/bot/fish")
public class FishController {

    @Autowired
    private FishProcessor processor;

    @RequestMapping(value = "")
    public BotResponseModel etaTime(BotRequestModel botReq) {

        BotResponseModel respModel = processor.main(botReq);

        // 增加 @
        respModel.setText(respModel.getText() + "  @" + botReq.getUser_name());

        return respModel;
    }
}
