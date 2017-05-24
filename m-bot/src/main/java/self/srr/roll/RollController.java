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
        String command = botReq.getText().contains("t1") ? "CARD" : "DICE";
        if (RollContrast.RATE_MAP.get(botReq.getUser_name() + command) != null) {
            RollContrast.ApiRate rate = RollContrast.RATE_MAP.get(botReq.getUser_name() + command);
            if ("DICE".equalsIgnoreCase(command) && nowTs - rate.getLastCall() <= 10) {
                // ROLL 点超过频率，禁止
                passFlg = false;
                log.info("User '" + botReq.getUser_name() + "' process skipped(Over limit)");
                respModel.setText("频率太快了，请等待一会儿（5 秒冷却）！");
                respModel = botRespDecorator.atDecorator(respModel, botReq.getUser_name());
            } else if ("CARD".equalsIgnoreCase(command) && rate.getCount() >= 10) {
                // 抽卡检测
                passFlg = false;
                log.info("User '" + botReq.getUser_name() + "' process skipped(Over limit)");
                respModel.setText("今天的十一连次数已满，请充值！（10 次）！");
                respModel = botRespDecorator.atDecorator(respModel, botReq.getUser_name());
            }
        }

        //如果合规
        if (passFlg) {
            log.info("User '" + botReq.getUser_name() + "' in process");
            respModel = rollProcessor.main(botReq);
            RollContrast.ApiRate lastRate = RollContrast.RATE_MAP.get(botReq.getUser_name() + command);
            if (RollContrast.RATE_MAP.get(botReq.getUser_name() + command) != null) {
                lastRate = RollContrast.RATE_MAP.get(botReq.getUser_name() + command);
            }
            RollContrast.ApiRate rate = new RollContrast.ApiRate();
            rate.setName(command);
            rate.setLastCall(nowTs);
            if ("DICE".equalsIgnoreCase(command)) {
                RollContrast.RATE_MAP.put(botReq.getUser_name() + command, rate);
            } else if ("CARD".equalsIgnoreCase(command)) {
                // 24 小时
                if (lastRate != null && lastRate.getLastCall() != null && nowTs - lastRate.getLastCall() < 3600 * 24) {
                    // 当日
                    rate.setCount(lastRate.getCount() + 1);
                } else {
                    rate.setCount(1);
                }
                RollContrast.RATE_MAP.put(botReq.getUser_name() + command, rate);
            }
        }

        return respModel;
    }
}
