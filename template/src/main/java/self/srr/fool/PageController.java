package self.srr.fool;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Sharuru on 2017/04/26.
 */
@Controller
@RequestMapping("/")
public class PageController {

    @RequestMapping("/test")
    String handler(
            Model model,
            @RequestParam(value = "t", required = false) String title,
            @RequestParam(value = "f", required = false) String from,
            @RequestParam(value = "t", required = false) String to,
            @RequestParam(value = "c", required = false) String content
    ) {
        model.addAttribute("title", title);
        model.addAttribute("content", content);
        return "view";
    }
}
