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
        } else if ("switch".equalsIgnoreCase(mainCommand)) {
            // 水晶充值
            botResp = switchDecorator(userCommand);
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

    /**
     * Roll 点检测策略
     * <p>
     * 检测用户两次请求间冷却时间是否有十秒
     *
     * @return 是否继续
     */
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

    /**
     * 抽卡检测策略
     * <p>
     * 根据抽卡策略检测用户余额
     *
     * @param cardPolicy 具体抽卡策略
     * @return 是否继续
     */
    private Boolean cardPolicyChecker(String cardPolicy) {
        RollRecord rollRecord = rollMapper.findOneByUid(botReq.getUser_id());
        if (rollRecord == null) {
            // 新纪录，插入
            Double freeAmount = 648.0 + new Random().nextInt(10000) / 100.0;
            Integer freeStone = 35 + new Random().nextInt(10);
            rollMapper.insertUser(botReq.getUser_id(), botReq.getUser_name(), freeAmount, freeStone);
            log.info("User '" + botReq.getUser_name() + "' free stone added");
        } else {
            if (RollContrast.CARD_SINGLE.equalsIgnoreCase(cardPolicy)) {
                // 单抽策略
                if (rollRecord.getStone() < RollContrast.CARD_SINGLE_COST) {
                    log.info("User '" + botReq.getUser_name() + "' process skipped(Insufficient stone)");
                    return false;
                }
            } else if (RollContrast.CARD_ELEVEN.equalsIgnoreCase(cardPolicy)) {
                // 十一连策略
                if (rollRecord.getStone() < RollContrast.CARD_SINGLE_COST * 11) {
                    log.info("User '" + botReq.getUser_name() + "' process skipped(Insufficient stone)");
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
                "`/roll card o` 进行一次单抽（3 水晶）；\n" +
                "`/roll card e` 进行十一连（30 水晶，十一连必出 UR）；\n" +
                "`/roll switch 30` 获取 30 水晶（180 元）；\n" +
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
        String type = userCommand.length > 1 && "e".equalsIgnoreCase(userCommand[1]) ? "e" : "o";
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
            // 连抽
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

            // 抽卡后扣款
            RollRecord rollRecord = rollMapper.findOneByUid(botReq.getUser_id());
            rollMapper.updateAmount(botReq.getUser_id(), rollRecord.getAmount(), rollRecord.getStone() - RollContrast.CARD_SINGLE_COST * times);
            log.info("User '" + botReq.getUser_name() + "' stone cost '" + RollContrast.CARD_SINGLE_COST * times + "'");

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

            String gotchaType = "o".equalsIgnoreCase(type) ? "单抽" : "十一连";
            resp.setText("「" + botReq.getUser_name() + "」这位大佬进行了一次" + gotchaType + "，获得了：" + resultStr + "。");
            if (result.contains("**UR**")) {
                String tailStr = africaFlag ? "（来自系统的怜悯）" : "";
                resp.setText(resp.getText() + "嚯哟！还是个欧皇！" + tailStr);
            }
        } else {
            log.info("User '" + botReq.getUser_name() + "' process skipped(Insufficient stone)");
            resp.setText("当前剩余水晶不足，无法抽卡。（使用 `/roll switch` 命令获取水晶）");
            resp = botRespDecorator.atDecorator(resp, botReq.getUser_name());
        }
        return resp;
    }

    private BotResponseModel switchDecorator(String[] userCommand) {
        BotResponseModel resp = new BotResponseModel();
        int switchCount = 1;
        if (userCommand.length > 1) {
            try {
                switchCount = Integer.valueOf(userCommand[1]);
            } catch (Exception e) {
                switchCount = 1;
            }
        }

        // 转换
        RollRecord rollRecord = rollMapper.findOneByUid(botReq.getUser_id());
        if (rollRecord.getAmount() >= switchCount * RollContrast.CARD_STONE_COST) {
            rollMapper.updateAmount(botReq.getUser_id(), rollRecord.getAmount() - switchCount * RollContrast.CARD_STONE_COST, rollRecord.getStone() + switchCount);
            log.info("User '" + botReq.getUser_name() + "' balance changed ' +" + switchCount + " stone, -" + switchCount * RollContrast.CARD_STONE_COST + " balance'");
            resp.setText("已通过消耗用户余额：" + switchCount * RollContrast.CARD_STONE_COST + " 元，获取：" + switchCount + " 枚水晶。");
        } else {
            resp.setText("当前用户余额不足，无法获取水晶！ [点击充值](" + RollContrast.CHARGE_URL + "?pass=" + botReq.getUser_id() + ")");
            log.info("User '" + botReq.getUser_name() + "' process skipped(Insufficient balance)");
            resp = botRespDecorator.atDecorator(resp, botReq.getUser_name());
        }
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
            // PY 交易
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
