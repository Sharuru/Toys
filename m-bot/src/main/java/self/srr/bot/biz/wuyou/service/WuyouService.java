package self.srr.bot.biz.wuyou.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import self.srr.bot.base.common.BotRequestModel;
import self.srr.bot.base.common.BotResponseModel;
import self.srr.bot.base.common.BotUtils;
import self.srr.bot.biz.wuyou.common.WuyouUtils;
import self.srr.bot.biz.wuyou.model.FundInfoResponseModel;

/**
 * Wuyou service
 * <p>
 * Created by Sharuru on 2021/02/19.
 */
@Service
@Slf4j
public class WuyouService {

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
        } else if ("jj".equalsIgnoreCase(action)) {
            botResponseModel = fundBiz(botResponseModel, args);
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
                "专业焦虑贩子，童叟无欺！\n" +
                "使用方法：\n" +
                "`/jl jj 基金代码` 查询对应基金的相关信息，数据延迟 5~10 分钟。\n" +
                "在指令后追加 s 表示将本次响应公开。";

        botResponseModel.setText(text);

        return botResponseModel;
    }

    /**
     * Fund biz
     *
     * @param botResponseModel prev.response
     * @param args             parameters
     * @return response
     */
    private BotResponseModel fundBiz(BotResponseModel botResponseModel, String[] args) {

        try {
            // parameter check
            if (args.length >= 2) {
                try {
                    String fundCode = args[1];
                    // request api
                    FundInfoResponseModel fundResponseModel = WuyouUtils.requestFundInfo(fundCode);
                    String text = "";
                    if (fundResponseModel.getErrCode() == 0 && fundResponseModel.isSuccess() && fundResponseModel.getDatas().size() == 1) {
                        FundInfoResponseModel.FundInfo fundInfo = fundResponseModel.getDatas().get(0);
                        if (fundInfo.getChangeRate().startsWith("-")) {
                            text += "炉火纯青，当收放自如。";
                        } else {
                            text += "不愧是阁下，英明的决断！";
                        }
                        text += "\n";
                        text += "基金代码：" + fundInfo.getFundCode() + "，基金名称：" + fundInfo.getFundName() + "。\n";
                        text += "基金净值：" + fundInfo.getNetValue() + "（" + fundInfo.getChangeRate() + "%），累计净值：" + fundInfo.getAccNetValue() + "。\n";
                        botResponseModel.setText(text);
                    } else {
                        botResponseModel.setText("嚯，你倒是先输入正确的基金代码啊。");
                    }
                } catch (Exception e) {
                    botResponseModel.setText("众人拾柴火焰高，咱们齐心协力，倒真能烧出个满天红霞来，不错不错。");
                    log.error("Error happened in 'fundBiz': " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                botResponseModel.setText("月有阴晴，扇有开合。穷寇莫追，焦虑未到。");
                log.error("Error happened in 'fundBiz': COMMAND_NOT_KNOWN: " + botRequestModel.getText());
            }
        } catch (Exception e) {
            botResponseModel.setText("气定，神闲。");
            log.error("Error happened in 'fundBiz': " + e.getMessage());
            e.printStackTrace();
        }

        return botResponseModel;
    }
}
