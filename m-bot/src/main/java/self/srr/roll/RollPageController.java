package self.srr.roll;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Roll 点页面路由
 * <p>
 * Created by Sharuru on 2017/5/27 0027.
 */
@Controller
@RequestMapping("/roll")
@Slf4j
public class RollPageController {

    @RequestMapping(value = "/switch", method = RequestMethod.GET)
    public String index() {
        return "roll/switch";
    }
}
