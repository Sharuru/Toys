package self.srr.fish;

import java.time.ZoneId;

/**
 * 摸鱼计时的一些常量
 * <p>
 * Created by Sharuru on 2017/4/29 0029.
 */
class FishContrast {

    // +8 时区
    static final ZoneId ZONE_SHANGHAI = ZoneId.of("Asia/Shanghai");

    // 24 小时秒数
    static final Long ONE_DAY = 86400L;

    // 9 小时秒数
    static final Long NINE_HOUR = 32400L;

    // 30 分钟秒数
    static final Long HALF_HOUR = 1800L;
}
