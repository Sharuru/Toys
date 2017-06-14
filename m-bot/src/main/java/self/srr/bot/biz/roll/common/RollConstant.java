package self.srr.bot.biz.roll.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Roll constants
 * <p>
 * Created by Sharuru on 2017/05/17.
 */
public class RollConstant {
    // 调用频率
    public static final Map<String, Long> DICE_RATE_MAP = new HashMap<>();

    // 抽卡类型
    public static final String CARD_SINGLE = "CARD_SINGLE";
    public static final String CARD_ELEVEN = "CARD_ELEVEN";

    // 抽卡价格
    public static final Integer CARD_SINGLE_COST = 3;
    public static final Integer CARD_STONE_COST = 6;

    // 充值地址
    public static final String CHARGE_URL = "http://172.17.229.212:12348/roll/switch";

    // 充值余额
    public static final FreeBalance FREE_BALANCE = new FreeBalance(0D, 0L);

    // 充值记录
    @Data
    public static class FreeBalance {
        FreeBalance(Double amount, Long lastCall) {
            this.amount = amount;
            this.lastCall = lastCall;
        }

        Double amount;
        Long lastCall;
    }
}
