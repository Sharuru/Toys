package self.srr.bot.biz.fish.common;

import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

/**
 * Fish constants
 * <p>
 * Created by Sharuru on 2017/4/29 0029.
 */
public class FishContrast {

    // API rate map
    public static final Map<String, Long> RATE_MAP = new HashMap<>();

    // +8 time zone
    public static final ZoneId ZONE_SHANGHAI = ZoneId.of("Asia/Shanghai");

    // seconds of 24 hours
    public static final Long ONE_DAY = 86400L;

    // seconds of 8 hours
    public static final Long NINE_HOUR = 32400L;

    // seconds of 30 minutes
    public static final Long HALF_HOUR = 1800L;

    public static final String KEY_SECOND = "SECOND";
    public static final String KEY_MINUTE = "MINUTE";
    public static final String KEY_HOUR = "HOUR";

}
