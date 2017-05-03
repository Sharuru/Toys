package self.srr.fish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import self.srr.common.BotContrast;
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
        BotResponseModel respModel = new BotResponseModel();
        Boolean passFlg = true;

        // 计算 API 频率
        Long nowTs = System.currentTimeMillis() / 1000;
        if (FishContrast.RATE_MAP.get(botReq.getUser_name()) != null) {
            Long lastTs = FishContrast.RATE_MAP.get(botReq.getUser_name());
            if (nowTs - lastTs <= 10) {
                // 超过频率，禁止
                passFlg = false;
                respModel.setText("频率太快了，请等待一会儿！  @" + botReq.getUser_name());
            }
        }

        // 如果合规
        if (passFlg) {
            respModel = processor.main(botReq);
            // 增加 @
            respModel.setText(respModel.getText() + "  @" + botReq.getUser_name());
            FishContrast.RATE_MAP.put(botReq.getUser_name(), nowTs);
        }

        // 如果 master
        if (botReq.getUser_name().equalsIgnoreCase(BotContrast.BOT_MASTER)) {
            respModel.setText("主人，" + respModel.getText());
        }

        return respModel;
    }
}
