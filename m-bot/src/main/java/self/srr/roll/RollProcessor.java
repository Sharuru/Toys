package self.srr.roll;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        } else if ("t1".equalsIgnoreCase(mainCommand)) {
            // 类型 1 抽卡
            botResp = cardDecorator(mainCommand);
        } else {
            // 普通 roll 点
            botResp = rollDecorator();
        }

        // 公开设置
        if ("s".equalsIgnoreCase(userCommand[userCommand.length - 1])) {
            botResp = botRespDecorator.publicDecorator(botResp);
        }

        return botResp;
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
     * @param type 种类名称
     * @return 响应实体
     */
    private BotResponseModel cardDecorator(String type) {
        BotResponseModel resp = new BotResponseModel();
        String result = "";
        // 根据类型抽卡
        if ("t1".equalsIgnoreCase(type)) {
            // 卡池设定
            List<Card> cards = new ArrayList<>();
            cards.add(new Card(1, "N", 0.28d));
            cards.add(new Card(2, "R", 0.27d));
            cards.add(new Card(3, "SR", 0.26d));
            cards.add(new Card(4, "SSR", 0.17d));
            cards.add(new Card(5, "UR", 0.01d));
            // 11 连
            for (int i = 0; i < 11; i++) {
                Double total = 0d;
                Double a = Math.random();
                for (Card aCard : cards) {
                    total += aCard.getProbability();
                    if (a < total) {
                        result += aCard.getName();
                        if (i < 10) {
                            result += ", ";
                        }
                        break;
                    }
                }
            }
        }
        log.info("User '" + botReq.getUser_name() + "' t1_gotcha: '" + result + "'");
        resp.setText("「" + botReq.getUser_name() + "」这大佬进行了十一连，获得了：" + result + "。");
        return resp;
    }

    /**
     * 1 - 100 点投掷
     *
     * @return 响应实体
     */
    private BotResponseModel rollDecorator() {
        BotResponseModel resp = new BotResponseModel();

        int roll = new Random().nextInt(100) + 1;
        log.info("User '" + botReq.getUser_name() + "' rolled: '" + roll + "'");
        resp.setText("「" + botReq.getUser_name() + "」掷出了：" + roll + " 点。");

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
