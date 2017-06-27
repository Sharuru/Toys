package self.srr.bot.biz.roll.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import self.srr.bot.base.common.BotRequestModel;
import self.srr.bot.base.common.BotResponseModel;
import self.srr.bot.base.common.BotUtils;
import self.srr.bot.base.entity.TblBotStock;
import self.srr.bot.base.repository.BotStockRepository;
import self.srr.bot.biz.roll.common.RollConstant;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Roll service
 * <p>
 * Created by Sharuru on 2017/06/14.
 */
@Service
@Slf4j
public class RollService {

    @Autowired
    private BotStockRepository botStockRepository;

    private BotRequestModel botRequestModel = new BotRequestModel();

    public BotResponseModel start(BotRequestModel botRequestModel) {
        this.botRequestModel = botRequestModel;

        //parse command
        String[] args = BotUtils.commandParser(botRequestModel.getText());
        String action = args[0];
        BotResponseModel botResponseModel = BotUtils.getDefaultResponseModel(args);

        log.info("User '" + botRequestModel.getUser_name() + "' inputted '" + botRequestModel.getText() + "'");

        // switch action
        if ("help".equalsIgnoreCase(action)) {
            botResponseModel = helpMsgBiz(botResponseModel);
        } else if ("card".equalsIgnoreCase(action)) {
            botResponseModel = cardBiz(botResponseModel, args);
        } else if ("status".equalsIgnoreCase(action)) {
            botResponseModel = statusBiz(botResponseModel);
        } else {
            botResponseModel = rollBiz(botResponseModel);
        }

        botResponseModel.appendAtWho(botRequestModel.getUser_name());

        return botResponseModel;
    }

    /**
     * Help message business
     *
     * @param botResponseModel prev. response
     * @return response
     */
    private BotResponseModel helpMsgBiz(BotResponseModel botResponseModel) {

        String text = "" +
                "Roll 点功能的帮助信息：\n" +
                "使用方法：输入 `/roll 指令。`\n" +
                "`/roll` 从 1-100 中随意 roll 出一种点数；\n" +
                "`/roll help` 显示本帮助信息；\n" +
                "`/roll card o` 进行一次单抽（3 水晶）；\n" +
                "`/roll card e` 进行十连（30 水晶）；\n" +
                "`/roll status` 查看个人帐户信息；\n" +
                "`/roll charge 30` 充值 30 水晶（180 元）；\n" +
                "在指令后追加 s 表示将本次响应公开；";

        botResponseModel.setText(text);

        return botResponseModel;
    }

    /**
     * Roll from 1-100 biz
     *
     * @param botResponseModel prev.response
     * @return response
     */
    private BotResponseModel rollBiz(BotResponseModel botResponseModel) {

        int number = new Random().nextInt(100) + 1;

        String text = "「" + botRequestModel.getUser_name() + "」掷出了：" + number + "点。";
        log.info("User: " + botResponseModel.getUsername() + " rolled " + number + " points.");

        botResponseModel.setText(text);

        return botResponseModel;
    }

    /**
     * Card gotcha biz
     *
     * @param botResponseModel prev.response
     * @param args             parameters
     * @return response
     */
    private BotResponseModel cardBiz(BotResponseModel botResponseModel, String[] args) {

        // user type check
        TblBotStock userStock = botStockRepository.findOneByUserIdAndItemId(botRequestModel.getUser_id(), RollConstant.TYPE_CRYSTAL);
        if (userStock == null) {
            // new user, first time free charge
            TblBotStock newUserStock = new TblBotStock();
            newUserStock.setUserId(botRequestModel.getUser_id());
            newUserStock.setItemId(RollConstant.TYPE_CRYSTAL);
            newUserStock.setItemCount(new BigDecimal(35 + new Random().nextInt(10)));
            userStock = botStockRepository.save(newUserStock);
            log.info("Free charge created: " + userStock.toString());
        }

        // roll type identify
        String rollType = (args.length >= 2 && "e".equalsIgnoreCase(args[1])) ? RollConstant.CARD_TEN : RollConstant.CARD_ONE;

        // balance check
        BigDecimal rollCost = RollConstant.CARD_ONE.equals(rollType) ? RollConstant.COST_ONE : RollConstant.COST_TEN;
        if (userStock.getItemCount().compareTo(rollCost) >= 0) {
            // enough, roll

            // make card list
            List<RollService.Card> cards = new ArrayList<>();
            cards.add(new RollService.Card(1, "N", 0.4d));
            cards.add(new RollService.Card(2, "R", 0.3d));
            cards.add(new RollService.Card(3, "SR", 0.18d));
            cards.add(new RollService.Card(4, "SSR", 0.11d));
            cards.add(new RollService.Card(5, "UR", 0.01d));

            // make result
            List<String> result = new ArrayList<>();

            // roll logic
            int rollTimes = RollConstant.CARD_ONE.equals(rollType) ? 1 : 10;
            for (int i = 0; i < rollTimes; i++) {
                Double total = 0d;
                Double a = Math.random();
                for (RollService.Card aCard : cards) {
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

            // after roll, charge
            userStock.setItemCount(userStock.getItemCount().subtract(rollCost));
            userStock = botStockRepository.save(userStock);
            log.info("User '" + botRequestModel.getUser_name() + "' cost CRYSTAL: " + rollCost + ", " + userStock.toString());

            // result trim
            StringBuilder resultStr = new StringBuilder();
            for (String card : result) {
                resultStr.append(card).append(", ");
            }
            resultStr = new StringBuilder(resultStr.substring(0, resultStr.length() - 2));
            log.info("User '" + botRequestModel.getUser_name() + "' gotcha result: '" + resultStr + "'");

            String gotchaType = RollConstant.CARD_ONE.equals(rollType) ? "单抽" : "十一连";
            botResponseModel.setText("「" + botRequestModel.getUser_name() + "」这位大佬进行了一次" + gotchaType + "，获得了：" + resultStr + "。");
            if (result.contains("**UR**")) {
                botResponseModel.setText(botResponseModel.getText() + "嚯哟！还是个欧皇！");
            }
        } else {
            // not enough
            botResponseModel.setText("当前水晶余额不足，无法抽卡。（使用 `/roll charge` 命令充值水晶）");
            log.info("User '" + botRequestModel.getUser_name() + "' biz skipped(INSUFFICIENT_BALANCE)");
        }

        return botResponseModel;
    }

    /**
     * Status biz
     *
     * @param botResponseModel prev.response
     * @return response
     */
    private BotResponseModel statusBiz(BotResponseModel botResponseModel) {

        //query record
        TblBotStock userAmount = botStockRepository.findOneByUserIdAndItemId(botRequestModel.getUser_id(), RollConstant.TYPE_MONEY);
        TblBotStock userCrystal = botStockRepository.findOneByUserIdAndItemId(botRequestModel.getUser_id(), RollConstant.TYPE_CRYSTAL);

        DecimalFormat df = new DecimalFormat();
        df.setMinimumFractionDigits(0);
        df.setMaximumFractionDigits(0);

        String text = "您好，" + botRequestModel.getUser_name() + "，" +
                "您当前的用户余额为：" + (userAmount == null ? "0.00" : userAmount.getItemCount()) + " 元，" +
                "水晶余额为：" + (userCrystal == null ? "0" : df.format(userCrystal.getItemCount())) + " 枚。";

        botResponseModel.setText(text);

        return botResponseModel;
    }


    /**
     * Card entity
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
