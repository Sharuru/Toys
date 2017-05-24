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
        Long nowTs = System.currentTimeMillis() / 1000;
        String command = botReq.getText().contains("t1") ? "CARD" : "DICE";     //TODO 命令解析, 整理
        String RATE_MAP_KEY = botReq.getUser_name() + command;
        RollContrast.ApiRate lastRate = RollContrast.RATE_MAP.get(RATE_MAP_KEY);

        // 计算 API 频率
        if ("DICE".equalsIgnoreCase(command)) {
            // 检查骰子频率
            if (lastRate != null && lastRate.getLastCall() != null) {
                // map 有数据，检测
                if (nowTs - lastRate.getLastCall() <= 10) {
                    // ROLL 点超过频率，禁止
                    passFlg = false;
                    log.info("User '" + botReq.getUser_name() + "' process skipped(Over limit)");
                    respModel.setText("频率太快了，请等待一会儿（10 秒冷却）！");
                    respModel = botRespDecorator.atDecorator(respModel, botReq.getUser_name());
                }
            } else {
                // 没数据，put
                RollContrast.RATE_MAP.put(RATE_MAP_KEY, new RollContrast.ApiRate("DICE", -1, nowTs));
            }
        } else if ("CARD".equalsIgnoreCase(command)) {
            // 检查抽卡频率
            if (lastRate != null && lastRate.getLastCall() != null) {
                // map 有数据，检测
                if (nowTs - lastRate.getLastCall() <= 3600 * 24) {
                    // 当日内
                    if (lastRate.getCount() >= 10) {
                        passFlg = false;
                        log.info("User '" + botReq.getUser_name() + "' process skipped(Over limit)");
                        respModel.setText("今天的十一连次数已满，请充值！（10 次）！");
                        respModel = botRespDecorator.atDecorator(respModel, botReq.getUser_name());
                    } else {
                        // 累加
                        RollContrast.RATE_MAP.put(RATE_MAP_KEY, new RollContrast.ApiRate("CARD", lastRate.getCount() + 1, nowTs));
                    }
                }
            } else {
                // 没数据，put
                RollContrast.RATE_MAP.put(RATE_MAP_KEY, new RollContrast.ApiRate("CARD", 1, nowTs));
            }
        }

        //如果合规
        if (passFlg) {
            log.info("User '" + botReq.getUser_name() + "' in process");
            respModel = rollProcessor.main(botReq);
        }

        return respModel;
    }
}
