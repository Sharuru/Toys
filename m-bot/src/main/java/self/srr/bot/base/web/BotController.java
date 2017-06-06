package self.srr.bot.base.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import self.srr.bot.base.common.BotContrast;

/**
 * Bot main router
 * <p>
 * Usually do nothing. ^_^
 * Created by Sharuru on 2017/04/28.
 */
@RestController
@RequestMapping("/api")
public class BotController {

    @RequestMapping("")
    public String index() {
        return BotContrast.BOT_DEFAULT_RESP;
    }
}
