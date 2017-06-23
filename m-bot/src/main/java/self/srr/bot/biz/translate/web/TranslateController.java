package self.srr.bot.biz.translate.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import self.srr.bot.base.common.BotRequestModel;
import self.srr.bot.base.common.BotResponseModel;

/**
 * Translate router
 * <p>
 * Created by Sharuru on 2017/06/22.
 */
@RestController
@RequestMapping("/bot/translate")
@Slf4j
public class TranslateController {

    @RequestMapping(value = "")
    public BotResponseModel translate(BotRequestModel botReq) {
        BotResponseModel botResponseModel;

        return null;
    }
}
