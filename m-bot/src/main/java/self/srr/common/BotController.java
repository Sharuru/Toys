package self.srr.common;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Bot 主路由
 * <p>
 * Created by Sharuru on 2017/04/28.
 */
@RestController
@RequestMapping("/bot")
public class BotController {

    @RequestMapping("")
    public String index() {
        return "Welcome to this BOT.";
    }
}
