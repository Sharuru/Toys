package self.srr.roll;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import self.srr.common.BotContrast;
import self.srr.common.BotRequestModel;
import self.srr.common.BotRespDecorator;
import self.srr.common.BotResponseModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Roll 点功能实现
 * <p>
 * Created by Sharuru on 2017/05/12.
 */
@Service
@Slf4j
public class RollProcessor {

    @Autowired
    private BotRespDecorator botRespDecorator;

    @Autowired
    private RollMapper rollMapper;

    private BotRequestModel botReq = new BotRequestModel();

    //主处理
    BotResponseModel main(BotRequestModel botReq) {
        this.botReq = botReq;
        BotResponseModel botResp = new BotResponseModel();
        log.info("User '" + botReq.getUser_name() + "' inputted '" + botReq.getText() + "'");

        // 解析参数以执行不同的行为
        String[] userCommand = botReq.getText().split("\\s+");
        String mainCommand = userCommand[0];

        // 选择行为
        if ("help".equalsIgnoreCase(mainCommand)) {
            // 显示帮助信息
            botResp = helpMsgDecorator();
        } else if ("card".equalsIgnoreCase(mainCommand)) {
            // 抽卡
            botResp = cardDecorator(userCommand);
        } else if ("reset".equalsIgnoreCase(mainCommand)) {
            // 管理员重置
            botResp = resetDecorator(userCommand);
        } else {
            // 普通 Roll 点
            botResp = rollDecorator(userCommand);


        }

        // 公开设置
        if ("s".equalsIgnoreCase(userCommand[userCommand.length - 1])) {
            botResp = botRespDecorator.publicDecorator(botResp);
        }

        return botResp;
    }

    private Boolean dicePolicyChecker() {
        Long nowTs = System.currentTimeMillis() / 1000;
        if (RollContrast.DICE_RATE_MAP.get(botReq.getUser_name()) != null) {
            Long lastTs = RollContrast.DICE_RATE_MAP.get(botReq.getUser_name());
            if (nowTs - lastTs <= 10) {
                // 超过频率，禁止
                log.info("User '" + botReq.getUser_name() + "' process skipped(Over limit)");
                return false;
            }
        }
        return true;
    }

    private Boolean cardPolicyChecker(String cardPolicy) {
        RollRecord rollRecord = rollMapper.findOneByUid(botReq.getUser_id());
        if (rollRecord == null) {
            // 新纪录，插入
            Double freeAmount = 648.0 + new Random().nextInt(10000) / 100.0;
            Integer freeStone = 3 + new Random().nextInt(10);
            rollMapper.insertUser(botReq.getUser_id(), botReq.getUser_name(), freeAmount, freeStone);
        } else {
            if (RollContrast.CARD_SINGLE.equalsIgnoreCase(cardPolicy)) {
                // 单抽策略
                if (rollRecord.getStone() < RollContrast.CARD_SINGLE_COST) {
                    return false;
                }
            } else if (RollContrast.CARD_ELEVEN.equalsIgnoreCase(cardPolicy)) {
                // 十一连策略
                if (rollRecord.getStone() < RollContrast.CARD_SINGLE_COST * 11) {
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * 显示帮助信息
     *
     * @return 响应实体
     */
    private BotResponseModel helpMsgDecorator() {
        BotResponseModel resp = new BotResponseModel();

        String text = "" +
                "Roll 点功能的帮助信息：\n" +
                "使用方法：输入 `/roll 指令。`\n" +
                "`/roll` 从 1-100 中随意 roll 出一种点数；\n" +
                "`/roll help` 显示本帮助信息；\n" +
                "`/roll t1` 进行种类 1 的十一连（N，R，SR，SSR，UR）；\n" +
                "在指令后追加 s 表示将本次响应公开；";
        resp.setText(text);

        return resp;
    }

    /**
     * 种类抽卡
     *
     * @param userCommand 用户命令
     * @return 响应实体
     */
    private BotResponseModel cardDecorator(String[] userCommand) {
        BotResponseModel resp = new BotResponseModel();
        List<String> result = new ArrayList<>();
        String type = userCommand[1];
        // 卡池设定
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(1, "N", 0.305d));
        cards.add(new Card(2, "R", 0.275d));
        cards.add(new Card(3, "SR", 0.285d));
        cards.add(new Card(4, "SSR", 0.126d));
        cards.add(new Card(5, "UR", 0.009d));
        // 根据类型抽卡
        if (("o".equalsIgnoreCase(type) && cardPolicyChecker(RollContrast.CARD_SINGLE)) || ("e".equalsIgnoreCase(type) && cardPolicyChecker(RollContrast.CARD_ELEVEN))) {
            int times = "o".equalsIgnoreCase(type) ? 1 : 11;
            // 抽卡连
            for (int i = 0; i < times; i++) {
                Double total = 0d;
                Double a = Math.random();
                for (Card aCard : cards) {
                    total += aCard.getProbability();
                    if (a < total) {
                        if ("SSR".equalsIgnoreCase(aCard.getName()) || "UR".equalsIgnoreCase(aCard.getName())) {
                            result.add("**" + aCard.getName() + "**");
                        } else {
                            result.add(aCard.getName());
                        }
                        break;
                    }
                }
            }

            // 十一连抽保底
            Boolean africaFlag = false;
            if ("e".equalsIgnoreCase(type) && !result.contains("**UR**")) {
                result.set(new Random().nextInt(10), "**UR**");
                africaFlag = true;
            }

            // 结果输出
            String resultStr = "";
            for (String card : result) {
                resultStr += card + ", ";
            }
            resultStr = resultStr.substring(0, resultStr.length() - 2);


            log.info("User '" + botReq.getUser_name() + "' card_gotcha: '" + resultStr + "'");

/*
        // PY 交易
        if (userCommand.length > 2 && "black".equalsIgnoreCase(userCommand[1]) && BotContrast.BOT_MASTER_ID.equalsIgnoreCase(botReq.getUser_id())) {
            String[] pyResult = userCommand[2].split(",");
            result = "";
            for (String aPyResult : pyResult) {
                result += aPyResult + ", ";
            }
            result = result.substring(0, result.length() - 2);
        }
*/
            String gotchaType = "o".equalsIgnoreCase(type) ? "单抽" : "十一连";
            resp.setText("「" + botReq.getUser_name() + "」这位大佬进行了一次" + gotchaType + "，获得了：" + resultStr + "。");
            if (result.contains("**UR**")) {
                String tailStr = africaFlag ? "（来自系统的怜悯）" : "";
                resp.setText(resp.getText() + "嚯哟！还是个欧皇！" + tailStr);
            }
        }
        return resp;
    }

    private BotResponseModel resetDecorator(String[] userCommand) {
        BotResponseModel resp = new BotResponseModel();
        if (userCommand.length > 1 && BotContrast.BOT_MASTER_ID.equalsIgnoreCase(botReq.getUser_id())) {
            RollContrast.ApiRate lastRate = RollContrast.RATE_MAP.get(userCommand[1] + "CARD");
            int count = 1;
            if (userCommand.length > 2) {
                count = Integer.valueOf(userCommand[2]);
            }
            RollContrast.RATE_MAP.put(userCommand[1] + "CARD", new RollContrast.ApiRate("CARD", lastRate.getCount() - count, lastRate.getLastCall()));
        }
        resp.setText("FINISH - " + userCommand[1] + "- " + RollContrast.RATE_MAP.get(userCommand[1] + "CARD").toString());
        return resp;
    }

    /**
     * 1 - 100 点投掷
     *
     * @param userCommand 用户命令
     * @return 响应实体
     */
    private BotResponseModel rollDecorator(String[] userCommand) {
        BotResponseModel resp = new BotResponseModel();
        if (dicePolicyChecker()) {
            int roll = new Random().nextInt(100) + 1;
            log.info("User '" + botReq.getUser_name() + "' rolled: '" + roll + "'");
            if (userCommand.length > 1 && "black".equalsIgnoreCase(userCommand[0]) && BotContrast.BOT_MASTER_ID.equalsIgnoreCase(botReq.getUser_id())) {
                roll = Integer.valueOf(userCommand[1]);
            }
            resp.setText("「" + botReq.getUser_name() + "」掷出了：" + roll + " 点。");
        } else {
            log.info("User '" + botReq.getUser_name() + "' process skipped(Over limit)");
            resp.setText("频率太快了，请等待一会儿（10 秒冷却）！");
            resp = botRespDecorator.atDecorator(resp, botReq.getUser_name());
        }

        return resp;
    }

    /**
     * 卡牌实体
     */
    @Data
    private class Card {
        int index;
        String name;
        double probability;

        Card(int index, String name, double probability) {
            this.index = index;
            this.name = name;
            this.probability = probability;
        }
    }
}
