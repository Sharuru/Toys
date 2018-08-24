package self.srr.bot.biz.roll.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import self.srr.bot.base.common.BotContrast;
import self.srr.bot.base.common.BotRequestModel;
import self.srr.bot.base.common.BotResponseModel;
import self.srr.bot.base.common.BotUtils;
import self.srr.bot.base.config.BotConfiguration;
import self.srr.bot.base.entity.TblBotStock;
import self.srr.bot.base.repository.BotStockRepository;
import self.srr.bot.biz.roll.common.RollConstant;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

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
        } else if ("charge".equalsIgnoreCase(action)) {
            botResponseModel = chargeBiz(botResponseModel, args);
        } else if ("b".equalsIgnoreCase(action)) {
            botResponseModel = bCountBiz(botResponseModel, args);
        } else if ("py".equalsIgnoreCase(action)) {
            botResponseModel = pyBiz(botResponseModel);
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
                "`/roll b 9,233` 从 9-233 中随意 roll 出一个 B 数，默认 1-100；\n" +
                "`/roll help` 显示本帮助信息；\n" +
                "`/roll card o` 进行一次单抽（40 " + BotContrast.BOT_CRYSTAL_TEXT + "）；\n" +
                "`/roll card e` 进行十连（300 " + BotContrast.BOT_CRYSTAL_TEXT + "，保底活动进行中）；\n" +
                "`/roll status` 查看个人帐户信息；\n" +
                "`/roll charge 10` 充值 10 " + BotContrast.BOT_CRYSTAL_TEXT + "（6 元）；\n" +
                "`/roll py` 和路西法进行 PY 交易以获得免费" + BotContrast.BOT_COIN_TEXT + "与" + BotContrast.BOT_CRYSTAL_TEXT + "；\n" +
                "目前卡池抽取概率：N 卡 40%，R 卡 30%，SR 卡 20%，SSR 卡 5%，UR 卡 1%（概率近似取得）；\n" +
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
     * B count from range
     *
     * @param botResponseModel prev.response
     * @param args             parameters
     * @return response
     */
    private BotResponseModel bCountBiz(BotResponseModel botResponseModel, String[] args) {

        int start = 0;
        int end = 100;

        // have input, not s:public
        if (args.length >= 2 && !args[1].equalsIgnoreCase("s")) {
            try {
                String[] range = args[1].split(",");
                start = Double.valueOf(range[0]).intValue();
                end = Double.valueOf(range[1]).intValue();
            } catch (Exception e) {
                botResponseModel.setText("你太事儿 B 了，无法测量。（参数输入错误）");
                log.info("User '" + botRequestModel.getUser_name() + "' biz skipped(ILLEGAL_PARAMS)");
                return botResponseModel;
            }
        }

        int number = ThreadLocalRandom.current().nextInt(start, end + 1);

        String text = botRequestModel.getUser_name() + "，你自己心里没点 B 数？（B 数值：" + number + "）";
        log.info("User: " + botResponseModel.getUsername() + " B counted " + number + " points.");

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
        TblBotStock userStoneStock = botStockRepository.findOneByUserIdAndItemId(botRequestModel.getUser_id(), RollConstant.TYPE_CRYSTAL);

        if (userStoneStock == null) {
            // new user, first time free charge
            TblBotStock newUserStockForRoll = new TblBotStock();
            newUserStockForRoll.setUserId(botRequestModel.getUser_id());
            newUserStockForRoll.setItemId(RollConstant.TYPE_CRYSTAL);
            newUserStockForRoll.setItemCount(new BigDecimal(400 + new Random().nextInt(10)));
            userStoneStock = botStockRepository.save(newUserStockForRoll);
            log.info("Free stone charge created: " + newUserStockForRoll.toString());
        }

        TblBotStock userMoneyStock = botStockRepository.findOneByUserIdAndItemId(botRequestModel.getUser_id(), RollConstant.TYPE_MONEY);

        if (userMoneyStock == null) {
            // new user, first time free charge
            TblBotStock newUserStock = new TblBotStock();
            newUserStock.setUserId(botRequestModel.getUser_id());
            newUserStock.setItemId(RollConstant.TYPE_MONEY);
            newUserStock.setItemCount(new BigDecimal(2400 + new Random().nextInt(100)));
            userMoneyStock = botStockRepository.save(newUserStock);
            log.info("Free money charge created: " + userMoneyStock.toString());
        }

        // roll type identify
        String rollType = (args.length >= 2 && "e".equalsIgnoreCase(args[1])) ? RollConstant.CARD_TEN : RollConstant.CARD_ONE;

        // balance check
        BigDecimal rollCost = RollConstant.CARD_ONE.equals(rollType) ? RollConstant.COST_ONE : RollConstant.COST_TEN;
        if (userStoneStock.getItemCount().compareTo(rollCost) >= 0) {
            // enough, roll

            // make card list
            List<RollService.Card> cards = new ArrayList<>();
            cards.add(new RollService.Card(1, "N", 0.4d));
            cards.add(new RollService.Card(2, "R", 0.33d));
            cards.add(new RollService.Card(3, "SR", 0.21d));
            cards.add(new RollService.Card(4, "SSR", 0.05d));
            cards.add(new RollService.Card(5, "UR", 0.01d));

            // make result
            List<String> result = new ArrayList<>();

            // roll logic
            boolean needSpecialHelp = true;
            int rollTimes = RollConstant.CARD_ONE.equals(rollType) ? 1 : 10;
            for (int i = 0; i < rollTimes; i++) {
                Double total = 0d;
                Double a = Math.random();
                for (RollService.Card aCard : cards) {
                    total += aCard.getProbability();
                    if (a < total) {
                        if ("SSR".equalsIgnoreCase(aCard.getName()) || "UR".equalsIgnoreCase(aCard.getName())) {
                            result.add("**" + aCard.getName() + "**");
                            needSpecialHelp = false;
                        } else {
                            result.add(aCard.getName());
                        }
                        break;
                    }
                }
            }

            // after roll, charge
            userStoneStock.setItemCount(userStoneStock.getItemCount().subtract(rollCost));
            userStoneStock = botStockRepository.save(userStoneStock);
            log.info("User '" + botRequestModel.getUser_name() + "' cost CRYSTAL: " + rollCost + ", " + userStoneStock.toString());

            // save africa user
            if (rollTimes >= 10 && needSpecialHelp) {
                result.set(result.size() - 1, "**SSR**");
            }

            // result trim
            String resultStr = result.stream().collect(Collectors.joining(","));
            log.info("User '" + botRequestModel.getUser_name() + "' gotcha result: '" + resultStr + "'");

            String gotchaType = RollConstant.CARD_ONE.equals(rollType) ? "单抽" : "十连";
            botResponseModel.setText("「" + botRequestModel.getUser_name() + "」这位大佬进行了一次" + gotchaType + "，获得了：" + resultStr + "。");
            if (result.contains("**UR**")) {
                botResponseModel.setText(botResponseModel.getText() + "嚯哟！还是个欧皇！ \n\n" + "![](" + BotUtils.getBotConfiguraion().getStorageLocation() + "/kakinn.png =100)");
            }
        } else {
            // not enough
            botResponseModel.setText("当前" + BotContrast.BOT_CRYSTAL_TEXT + "余额不足，无法抽卡。（使用 `/roll charge` 命令充值" + BotContrast.BOT_CRYSTAL_TEXT + "）");
            log.info("User '" + botRequestModel.getUser_name() + "' biz skipped(INSUFFICIENT_BALANCE)");
        }

        return botResponseModel;
    }

    /**
     * PY trade biz
     *
     * @param botResponseModel prev. response
     * @return new response
     */
    private BotResponseModel pyBiz(BotResponseModel botResponseModel) {

        TblBotStock userMoneyStock = botStockRepository.findOneByUserIdAndItemId(botRequestModel.getUser_id(), RollConstant.TYPE_MONEY);

        if (userMoneyStock == null) {
            // new user, first time free charge
            TblBotStock newUserStock = new TblBotStock();
            newUserStock.setItemId(RollConstant.TYPE_MONEY);
            newUserStock.setUserId(botRequestModel.getUser_id());
            newUserStock.setItemCount(new BigDecimal(2400 + new Random().nextInt(100)));
            userMoneyStock = botStockRepository.save(newUserStock);
            botResponseModel.setText("与路西法的 PY 交易已完成，灵视 +1。");
            log.info("Free money charge created: " + userMoneyStock.toString());
            return botResponseModel;
        } else {
            BigDecimal userMoney = userMoneyStock.getItemCount();
            if (userMoney.compareTo(new BigDecimal(1000000)) > 0) {
                botResponseModel.setText("你已经比路西法富有了，因此路西法不想与你交易，人性 -1。");
                return botResponseModel;
            } else {
                BigDecimal freeMoney = new BigDecimal(10000 + new Random().nextInt(2500));
                userMoney = userMoney.add(freeMoney);
                userMoneyStock.setItemCount(userMoney);
                userMoneyStock = botStockRepository.save(userMoneyStock);
                //botResponseModel.setText("与路西法的 PY 交易已完成，灵视 +5。");
                log.info("Free money charge charged(+" + freeMoney.toPlainString() + "): " + userMoneyStock.toString());
            }
        }

        TblBotStock userStoneStock = botStockRepository.findOneByUserIdAndItemId(botRequestModel.getUser_id(), RollConstant.TYPE_CRYSTAL);

        if (userStoneStock == null) {
            // new user, first time free charge
            TblBotStock newUserStock = new TblBotStock();
            newUserStock.setItemId(RollConstant.TYPE_CRYSTAL);
            newUserStock.setUserId(botRequestModel.getUser_id());
            newUserStock.setItemCount(new BigDecimal(400 + new Random().nextInt(10)));
            userStoneStock = botStockRepository.save(newUserStock);
            botResponseModel.setText("与路西法的 PY 交易已完成，人性 +2。");
            log.info("Free stone charge created: " + userStoneStock.toString());
            return botResponseModel;
        } else {
            BigDecimal userStone = userStoneStock.getItemCount();
            if (userStone.compareTo(new BigDecimal(100000)) > 0) {
                botResponseModel.setText("“你的石头已经足够填海了。”路西法这样说道。（PY 交易失败，灵视 -1。）");
                return botResponseModel;
            } else {
                BigDecimal freeStone = new BigDecimal(500 + new Random().nextInt(500));
                userStone = userStone.add(freeStone);
                userStoneStock.setItemCount(userStone);
                userStoneStock = botStockRepository.save(userStoneStock);
                botResponseModel.setText("与路西法的 PY 交易已完成，人性 +3。");
                log.info("Free stone charge charged(+" + freeStone.toPlainString() + "): " + userStoneStock.toString());
            }
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
        df.setGroupingUsed(false);
        df.setMinimumFractionDigits(0);
        df.setMaximumFractionDigits(0);

        String text = "您好，" + botRequestModel.getUser_name() + "，" +
                "您当前的用户余额为：" + (userAmount == null ? "0.00" : userAmount.getItemCount()) + " 元，" +
                BotContrast.BOT_CRYSTAL_TEXT + "余额为：" + (userCrystal == null ? "0" : df.format(userCrystal.getItemCount())) + " 枚。";

        botResponseModel.setText(text);

        return botResponseModel;
    }

    /**
     * Charge biz
     *
     * @param botResponseModel prev.response
     * @param args             parameters
     * @return response
     */
    private BotResponseModel chargeBiz(BotResponseModel botResponseModel, String[] args) {

        int charge = 1;
        if (args.length >= 2) {
            try {
                charge = Integer.valueOf(args[1]);
            } catch (Exception e) {
                charge = 1;
            }
        }

        //query record
        TblBotStock userAmount = botStockRepository.findOneByUserIdAndItemId(botRequestModel.getUser_id(), RollConstant.TYPE_MONEY);
        TblBotStock userCrystal = botStockRepository.findOneByUserIdAndItemId(botRequestModel.getUser_id(), RollConstant.TYPE_CRYSTAL);

        if (userAmount != null && userAmount.getItemCount().compareTo(RollConstant.COST_CRYSTAL.multiply(new BigDecimal(charge))) >= 0) {
            // charge
            if (userCrystal == null) {
                // first charge
                userCrystal = new TblBotStock();
                userCrystal.setUserId(botRequestModel.getUser_id());
                userCrystal.setItemId(RollConstant.TYPE_CRYSTAL);
                userCrystal.setItemCount(new BigDecimal(35 + new Random().nextInt(10)));
            }
            userAmount.setItemCount(userAmount.getItemCount().subtract(RollConstant.COST_CRYSTAL.multiply(new BigDecimal(charge))));
            userCrystal.setItemCount(userCrystal.getItemCount().add(new BigDecimal(charge)));

            userAmount = botStockRepository.save(userAmount);
            log.info("User '" + botRequestModel.getUser_name() + "' cost " + RollConstant.COST_CRYSTAL.multiply(new BigDecimal(charge)) + " money." + userAmount.toString());
            userCrystal = botStockRepository.save(userCrystal);
            log.info("User '" + botRequestModel.getUser_name() + "' charged " + charge + " crystals." + userCrystal.toString());

            botResponseModel.setText("已花费：" + RollConstant.COST_CRYSTAL.multiply(new BigDecimal(charge)) + " 元，获得：" + charge + " 枚" + BotContrast.BOT_CRYSTAL_TEXT + "。");

        } else {
            botResponseModel.setText("用户余额不足，充值失败。");
            log.info("User '" + botRequestModel.getUser_name() + "' biz skipped(INSUFFICIENT_BALANCE)");
        }

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
