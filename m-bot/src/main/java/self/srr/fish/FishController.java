package self.srr.fish;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import self.srr.common.BotRequestModel;
import self.srr.common.BotRespDecorator;
import self.srr.common.BotResponseModel;

/**
 * 摸鱼计时路由
 * <p>
 * Created by Sharuru on 2017/04/28.
 */
@RestController
@RequestMapping("/bot/fish")
@Slf4j
public class FishController {

    @Autowired
    private FishProcessor processor;

    @Autowired
    private BotRespDecorator botRespDecorator;

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
                log.info("User '" + botReq.getUser_name() + "' process skipped(Over limit)");
                respModel.setText("频率太快了，请等待一会儿！");
                respModel = botRespDecorator.atDecorator(respModel, botReq.getUser_name());
            }
        }

        // 如果合规
        if (passFlg) {
            log.info("User '" + botReq.getUser_name() + "' in process");
            respModel = processor.main(botReq);
            FishContrast.RATE_MAP.put(botReq.getUser_name(), nowTs);
        }

        log.info("User '" + botReq.getUser_name() + "' process finished");

        return respModel;
    }
}
