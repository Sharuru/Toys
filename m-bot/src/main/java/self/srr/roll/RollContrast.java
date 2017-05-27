package self.srr.roll;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Roll 的一些常量
 * <p>
 * Created by Sharuru on 2017/05/17.
 */
class RollContrast {
    // 调用频率
    static final Map<String, ApiRate> RATE_MAP = new HashMap<>();
    static final Map<String, Long> DICE_RATE_MAP = new HashMap<>();

    // 抽卡类型
    static final String CARD_SINGLE = "CARD_SINGLE";
    static final String CARD_ELEVEN = "CARD_ELEVEN";

    // API 频率记录
    @Data
    static class ApiRate {

        ApiRate(String name, int count, Long lastCall) {
            this.name = name;
            this.count = count;
            this.lastCall = lastCall;
        }

        String name;
        int count;
        Long lastCall;
    }
}
