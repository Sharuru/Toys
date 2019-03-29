package me.sharuru.mattermost.sum.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/")
@Slf4j
public class PageController {




    @GetMapping("/")
    public String index(){
        return "index";
    }
}
