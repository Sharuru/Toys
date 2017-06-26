package self.srr.bot.biz.translate.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import self.srr.bot.base.common.BotRequestModel;
import self.srr.bot.base.common.BotResponseModel;
import self.srr.bot.biz.translate.service.TranslateService;

/**
 * Translate router
 * <p>
 * Created by Sharuru on 2017/06/22.
 */
@RestController
@RequestMapping("/api/translate")
@Slf4j
public class TranslateController {

    @Autowired
    TranslateService translateService;

    @RequestMapping(value = "")
    public BotResponseModel translate(BotRequestModel botRequestModel) {
        BotResponseModel botResponseModel;

        botResponseModel = translateService.start(botRequestModel);

        return botResponseModel;
    }
}
