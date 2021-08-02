package me.sharuru.srrbot.common;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class SrrBotUtils {
    
    @Value("${srrBot.authKey}")
    public String authKey = "";
    @Value("${srrBot.botQq}")
    public String botQq = "";
    @Value("${srrBot.apiEndpoint}")
    public String apiEndpoint = "";

    private String sessionKey;
}
