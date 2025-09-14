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

        // 立即返回确认消息，避免输入框卡顿
        CommandResponsePayload immediateResponse = new CommandResponsePayload();
        immediateResponse.setText("翻译请求已提交，请留意后续信息...");

        // 异步处理翻译任务，使用 OpenAI 官方异步 API
        translateService.processTranslationAsync(payload);

        log.info("[Translate] returned immediate response");
        return immediateResponse;
    }
}
