package me.sharuru.matterslash.web;

import lombok.extern.slf4j.Slf4j;
import me.sharuru.matterslash.model.CommandRequestPayload;
import me.sharuru.matterslash.model.CommandResponsePayload;
import me.sharuru.matterslash.service.TranslateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequestMapping("/commands")
public class CommandController {

    @Autowired
    TranslateService translateService;

    @ResponseBody
    @RequestMapping("/translate")
    public CommandResponsePayload translate(final CommandRequestPayload payload) {
        log.info("[Translate] payload: {}", payload.toString());
        CommandResponsePayload response = new CommandResponsePayload();
        response.setText(translateService.translate(payload.getText().trim()));
        log.info("[Translate] returned: {}", response.getText());
        return response;
    }
}
