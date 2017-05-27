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
    static final Map<String, Long> DICE_RATE_MAP = new HashMap<>();

    // 抽卡类型
    static final String CARD_SINGLE = "CARD_SINGLE";
    static final String CARD_ELEVEN = "CARD_ELEVEN";

    // 抽卡价格
    static final Integer CARD_SINGLE_COST = 3;
    static final Integer CARD_STONE_COST = 6;

    // 充值地址
    static final String CHARGE_URL = "http://127.0.0.1/roll/switch";

    // 充值余额
    static final FreeBalance FREE_BALANCE = new FreeBalance(0D, 0L);

    // 充值记录
    @Data
    static class FreeBalance {
        FreeBalance(Double amount, Long lastCall) {
            this.amount = amount;
            this.lastCall = lastCall;
        }

        Double amount;
        Long lastCall;
    }
}
