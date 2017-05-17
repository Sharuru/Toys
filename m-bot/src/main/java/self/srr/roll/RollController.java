package self.srr.roll;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import self.srr.common.BotRequestModel;
import self.srr.common.BotRespDecorator;
import self.srr.common.BotResponseModel;

/**
 * Roll 点路由
 * <p>
 * Created by Sharuru on 2017/05/12.
 */
@RestController
@RequestMapping("/bot/roll")
@Slf4j
public class RollController {

    @Autowired
    private RollProcessor rollProcessor;

    @Autowired
    private BotRespDecorator botRespDecorator;

    @RequestMapping(value = "")
    public BotResponseModel roll(BotRequestModel botReq) {
        BotResponseModel respModel = new BotResponseModel();
        Boolean passFlg = true;

        // 计算 API 频率
        Long nowTs = System.currentTimeMillis() / 1000;
        if (RollContrast.RATE_MAP.get(botReq.getUser_name()) != null) {
            Long lastTs = RollContrast.RATE_MAP.get(botReq.getUser_name());
            if (nowTs - lastTs <= 5) {
                // 超过频率，禁止
                passFlg = false;
                log.info("User '" + botReq.getUser_name() + "' process skipped(Over limit)");
                respModel.setText("频率太快了，请等待一会儿（5 秒冷却）！");
                respModel = botRespDecorator.atDecorator(respModel, botReq.getUser_name());
            }
        }

        //如果合规
        if (passFlg) {
            log.info("User '" + botReq.getUser_name() + "' in process");
            respModel = rollProcessor.main(botReq);
            RollContrast.RATE_MAP.put(botReq.getUser_name(), nowTs);
        }

        return respModel;
    }
}
