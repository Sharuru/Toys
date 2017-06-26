package self.srr.bot.biz.translate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import self.srr.bot.base.common.BotRequestModel;
import self.srr.bot.base.common.BotResponseModel;
import self.srr.bot.base.common.BotUtils;
import self.srr.bot.biz.translate.common.TranslateConstant;
import self.srr.bot.biz.translate.common.TranslateUtils;
import self.srr.bot.biz.translate.model.TranslateResponseModel;

/**
 * Translate service
 * <p>
 * Created by Sharuru on 2017/06/23.
 */
@Service
@Slf4j
public class TranslateService {

    private BotRequestModel botRequestModel = new BotRequestModel();

    public BotResponseModel start(BotRequestModel botRequestModel) {
        this.botRequestModel = botRequestModel;

        // parse command
        String[] args = BotUtils.commandParser(botRequestModel.getText());
        String action = args[0];
        BotResponseModel botResponseModel = BotUtils.getDefaultResponseModel(args);

        log.info("User '" + botRequestModel.getUser_name() + "' inputted '" + botRequestModel.getText() + "'");

        // switch action
        if ("help".equalsIgnoreCase(action)) {
            botResponseModel = helpMsgBiz(botResponseModel);
        } else {
            botResponseModel = translateBiz(botResponseModel, args);
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
                "翻译功能的帮助信息：\n" +
                "使用方法：输入 `/translate 语种 文本。`\n" +
                "`/translate jp2zh こんにちは` 将日语こんにちは翻译成中文。\n" +
                "在原文文本少于 100 字的情况下，会自动提供一份英文翻译以辅助参考。\n" +
                "目前支持的语种如下：\n" +
                "\n" +
                "| 语种代码    | 名称                     |\n" +
                "|:------------|:-------------------------|\n" +
                "| zh          | 中文                     |\n" +
                "| en          | 英语                     |\n" +
                "| jp          | 日语                     |\n" +
                "| de          | 德语                     |\n" +
                "| ru          | 俄语                     |\n" +
                "| cht         | 繁体中文                 |\n";

        botResponseModel.setText(text);

        return botResponseModel;
    }

    /**
     * Translate biz
     *
     * @param botResponseModel prev.response
     * @param args             parameters
     * @return response
     */
    private BotResponseModel translateBiz(BotResponseModel botResponseModel, String[] args) {

        String fromLangCode = null;
        String toLangCode = null;
        Boolean passFlag = false;

        try {
            // parameter check
            if (args.length >= 2) {      // user identified language code
                String[] langCode = args[0].split("2");
                if (langCode.length != 2) {
                    botResponseModel.setText("语种设置错误，请确认。");
                    log.error("Error happened in 'translateBiz': LANG_CODE_INCORRECT: " + args[0]);
                } else {
                    if (!TranslateConstant.supportedLangCode.contains(langCode[0]) || !TranslateConstant.supportedLangCode.contains(langCode[1])) {
                        // TODO Experiment method, current master only
                        if (!BotUtils.isUserMaster(botRequestModel.getUser_id())) {
                            botResponseModel.setText("该语种（" + langCode[0] + " -> " + langCode[1] + " ）暂时不支持，请确认。");
                            log.error("Error happened in 'translateBiz': LANG_CODE_NOT_SUPPORTED: " + langCode[0] + " -> " + langCode[1]);
                        }
                    } else {
                        fromLangCode = langCode[0];
                        toLangCode = langCode[1];
                        passFlag = true;
                    }
                }
            } else {
                botResponseModel.setText("指令输入错误，请确认。");
                log.error("Error happened in 'translateBiz': COMMAND_NOT_KNOWN: " + botRequestModel.getText());
            }

            // request api
            if (passFlag) {
                StringBuilder srcText = new StringBuilder();
                // TODO English space support, but CJK not working well
                for (int i = 1; i < args.length; i++) {
                    if (!"s".equalsIgnoreCase(args[i])) {
                        srcText.append(args[i]);
                        srcText.append(" ");
                    }
                }

                TranslateResponseModel responseModel = TranslateUtils.requestApi(srcText.toString(), fromLangCode, toLangCode);
                if (responseModel.getError_code() == null) {
                    // no error
                    String text = "翻译结果：[" + fromLangCode + "]" + srcText + " \n-> [" + toLangCode + "]" + responseModel.getTrans_result().get(0).getDst();
                    // if short, give addition example
                    if (!"en".equalsIgnoreCase(fromLangCode) && !"en".equalsIgnoreCase(toLangCode) && "zh".equalsIgnoreCase(toLangCode) && srcText.length() <= 100) {
                        TranslateResponseModel additionalResponseModel = TranslateUtils.requestApi(args[1], fromLangCode, "en");
                        text += "\n";
                        text += "参考结果：[" + fromLangCode + "]" + srcText + " \n-> [en]" + additionalResponseModel.getTrans_result().get(0).getDst();
                    }
                    botResponseModel.setText(text);
                } else {
                    botResponseModel.setText("查询时发生了异常，" + responseModel.toString());
                    log.error("Error happened in `translateBiz`: " + responseModel.toString());
                }
            }
        } catch (Exception e) {
            botResponseModel.setText("似乎发生了奇怪的问题，麻烦稍后再试。");
            log.error("Error happened in 'translateBiz': " + e.getMessage());
            e.printStackTrace();
        }

        return botResponseModel;
    }

}
