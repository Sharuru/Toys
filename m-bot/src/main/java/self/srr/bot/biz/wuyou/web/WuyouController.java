package self.srr.bot.biz.wuyou.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import self.srr.bot.base.common.BotRequestModel;
import self.srr.bot.base.common.BotResponseModel;
import self.srr.bot.biz.wuyou.service.WuyouService;

/**
 * Wuyou router
 * <p>
 * Created by Sharuru on 2021/02/19.
 */
@RestController
@RequestMapping("/api/wuyou")
@Slf4j
public class WuyouController {

    @Autowired
    WuyouService wuyouService;

    @RequestMapping(value = "")
    public BotResponseModel fund(BotRequestModel botRequestModel) {
        BotResponseModel botResponseModel;

        botResponseModel = wuyouService.start(botRequestModel);

        return botResponseModel;
    }
}
