package self.srr.bot.base.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import self.srr.bot.base.entity.TblBotItem;
import self.srr.bot.base.repository.BotItemRepository;

import java.util.List;

/**
 * Bot contrasts
 * <p>
 * Created by Sharuru on 2017/4/30 0030.
 */
@Component
@Slf4j
public class BotContrast {

    @Autowired
    BotItemRepository botItemRepository;

    // response type
    public static final String BOT_RESP_TYPE_ICH = "in_channel";     // visible to everyone
    public static final String BOT_RESP_TYPE_EPH = "ephemeral";      // only to sender

    // default response context
    public static final String BOT_DEFAULT_RESP = "RESPONSE_OK";

    // default text
    public static String BOT_COIN_TEXT = "金钱";
    public static String BOT_CRYSTAL_TEXT = "水晶";

    @EventListener(ApplicationReadyEvent.class)
    public void initializeText() {
        List<TblBotItem> items = botItemRepository.findAll();
        items.stream().filter(i -> "COIN".equals(i.getItemType())).findFirst().ifPresent(i -> {
            log.info("Changing BOT_COIN_TEXT from {} to {}", BOT_COIN_TEXT, i.getItemName());
            BOT_COIN_TEXT = i.getItemName();
        });
        items.stream().filter(i -> "CRYSTAL".equals(i.getItemType())).findFirst().ifPresent(i -> {
            log.info("Changing BOT_CRYSTAL_TEXT from {} to {}", BOT_CRYSTAL_TEXT, i.getItemName());
            BOT_CRYSTAL_TEXT = i.getItemName();
        });
    }

}
