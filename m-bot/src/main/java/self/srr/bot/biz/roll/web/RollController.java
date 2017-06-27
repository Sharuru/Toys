package self.srr.bot.biz.roll.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import self.srr.bot.base.common.BotRequestModel;
import self.srr.bot.base.common.BotResponseModel;
import self.srr.bot.base.common.BotUtils;
import self.srr.bot.biz.roll.service.RollService;
import self.srr.roll.RollProcessor;

/**
 * Roll router
 * <p>
 * Created by Sharuru on 2017/05/12.
 */
@RestController
@RequestMapping("/api/roll")
@Slf4j
public class RollController {

    @Autowired
    RollService rollService;

    @RequestMapping(value = "")
    public BotResponseModel roll(BotRequestModel botRequestModel) {
        BotResponseModel botResponseModel = BotUtils.getDefaultResponseModel();

        // TODO re-design
        //botResponseModel.setText("该功能正在回炉重做。");
        //botResponseModel.appendAtWho(botReq.getUser_name());
        botResponseModel = rollService.start(botRequestModel);

        return botResponseModel;
    }
}
