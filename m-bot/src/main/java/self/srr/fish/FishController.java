package self.srr.fish;

import org.springframework.web.bind.annotation.*;
import self.srr.common.BotRequestModel;
import self.srr.common.BotResponseModel;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.TimeZone;

/**
 * Created by Sharuru on 2017/04/28.
 */
@RestController
@RequestMapping("/bot/fish")
public class FishController {

    @RequestMapping(value = "")
    public BotResponseModel etaTime(BotRequestModel botReq) {
        BotResponseModel model = new BotResponseModel();
        model.setResponse_type("in_channel");

        String context = "";

        // calc time
        LocalDateTime now = LocalDateTime.now();
        ZonedDateTime endTime = ZonedDateTime.of(now.getYear(),now.getMonthValue(),now.getDayOfMonth(),18,00,00,00, ZoneId.of("Asia/Shanghai"));
        LocalDateTime end = endTime.withZoneSameInstant(ZoneId.of(TimeZone.getDefault().getID())).toLocalDateTime();
        Duration duration = Duration.between(now, end);

        context += "哎呀，还得摸：" + String.valueOf(duration.getSeconds()) + " 秒";
        if(duration.getSeconds() > 60 && duration.getSeconds() < 3600){
            context += "，约 " + (duration.getSeconds()/60) + " 分钟";
        }
        if(duration.getSeconds() > 3600){
            context += "，约 " + (duration.getSeconds()/3600) + " 小时";
        }
        model.setText(context);
        model.setUsername("Bot");
        model.setIcon_url("http://127.0.0.1/");
        return model;
    }
}
