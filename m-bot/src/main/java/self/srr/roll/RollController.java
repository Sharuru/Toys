package self.srr.roll;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import self.srr.common.BotRequestModel;
import self.srr.common.BotRespDecorator;
import self.srr.common.BotResponseModel;

/**
 * Roll 点路由
 * <p>
 * Created by Sharuru on 2017/05/12.
 */
@RestController
@RequestMapping("/bot/roll")
@Slf4j
public class RollController {

    @Autowired
    private RollProcessor rollProcessor;

    @Autowired
    private BotRespDecorator botRespDecorator;

    @RequestMapping(value = "")
    public BotResponseModel roll(BotRequestModel botReq) {
        BotResponseModel respModel = new BotResponseModel();
        log.info("User '" + botReq.getUser_name() + "' in process");
        respModel = rollProcessor.main(botReq);
        log.info("User '" + botReq.getUser_name() + "' process finished");
        return respModel;
    }
}
