package self.srr.bot.biz.fish.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import self.srr.bot.base.common.BotRequestModel;
import self.srr.bot.base.common.BotResponseModel;
import self.srr.bot.base.common.BotUtils;
import self.srr.bot.biz.fish.service.FishService;

/**
 * Fish router
 * <p>
 * Created by Sharuru on 2017/06/07.
 */
@RestController
@RequestMapping("/api/fish")
@Slf4j
public class FishController {

    @Autowired
    private FishService fishService;

    @RequestMapping(value = "")
    public BotResponseModel test(BotRequestModel botRequestModel){
        BotResponseModel botResponseModel;

        botResponseModel = fishService.start(botRequestModel);

        return  botResponseModel;
    }
}
