package self.srr.bot.biz.translate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import self.srr.bot.base.common.BotRequestModel;
import self.srr.bot.base.common.BotResponseModel;
import self.srr.bot.base.common.BotUtils;
import self.srr.bot.biz.translate.common.TranslateConstant;

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
                "在原文文本较少的情况下，会自动提供一份英文翻译以辅助参考。\n" +
                "目前支持的语种参考下表：\n" +
                "| 语种代码 |  名称   | \n" +
                "| :-------:|:-------:| \n" +
                "|    zh    |  中文   | \n" +
                "|    en    |  英语   | \n" +
                "|    jp    |  日语   | \n" +
                "|    de    |  德语   | \n" +
                "|    ru    |  俄语   | \n" +
                "|    cht   | 繁体中文| \n";

        botResponseModel.setText(text);

        return botResponseModel;
    }

    private BotResponseModel translateBiz(BotResponseModel botResponseModel, String[] args) {

        String fromLangCode;
        String toLangCode;

        try {
            // parameter check
            if (args.length >= 2) {      // user identified language code
                String[] langCode = args[0].split("2");
                if (langCode.length != 2) {
                    botResponseModel.setText("语种设置错误，请确认。");
                    log.error("Error happened in 'translateBiz': LANG_CODE_INCORRECT: " + args[0]);
                } else {
                    if (!TranslateConstant.supportedLangCode.contains(langCode[0]) || !TranslateConstant.supportedLangCode.contains(langCode[1])) {
                        botResponseModel.setText("该语种（" + langCode[0] + " -> " + langCode[1] + " ）暂时不支持，请确认。");
                        log.error("Error happened in 'translateBiz': LANG_CODE_NOT_SUPPORTED: " + langCode[0] + " -> " + langCode[1]);
                    } else {
                        fromLangCode = langCode[0];
                        toLangCode = langCode[1];
                    }
                }
            } else {
                botResponseModel.setText("指令输入错误，请确认。");
                log.error("Error happened in 'translateBiz': COMMAND_NOT_KNOWN: " + botRequestModel.getText());
            }

        } catch (Exception e) {
            botResponseModel.setText("似乎发生了奇怪的问题，麻烦稍后再试。");
            log.error("Error happened in 'translateBiz': " + e.getMessage());
            e.printStackTrace();
        }

        return botResponseModel;
    }

}
