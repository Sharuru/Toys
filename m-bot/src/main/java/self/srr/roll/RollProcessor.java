package self.srr.roll;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import self.srr.common.BotRequestModel;
import self.srr.common.BotRespDecorator;
import self.srr.common.BotResponseModel;

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
    BotResponseModel main(BotRequestModel botReq){
        this.botReq = botReq;
        BotResponseModel botResp = new BotResponseModel();

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

        // @设置
        botResp = botRespDecorator.atDecorator(botResp, botReq.getUser_name());

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
                "`/roll t1` 进行种类 1 的 11 连（N，R，SR，SSR，UR）；\n" +
                "在指令后追加 s 表示将本次响应公开；";
        resp.setText(text);

        return resp;
    }


    private BotResponseModel cardDecorator(String type){
        BotResponseModel resp = new BotResponseModel();

        // 根据类型抽卡
        if("t1".equalsIgnoreCase(type)){
            // N，R，SR，SSR，UR 抽卡
        }

        return resp;
    }



    public class Card{
        String id;
        Float chance;
    }
}
