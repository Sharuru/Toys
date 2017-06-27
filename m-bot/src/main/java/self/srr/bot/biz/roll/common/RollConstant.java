package self.srr.bot.biz.roll.common;

import java.math.BigDecimal;

/**
 * Roll constants
 * <p>
 * Created by Sharuru on 2017/05/17.
 */
public class RollConstant {

    // item type
    public static final Long TYPE_CRYSTAL = 2L;
    public static final Long TYPE_MONEY = 3L;

    // roll type
    public static final String CARD_ONE = "CARD_ONE";
    public static final String CARD_TEN = "CARD_TEN";

    // roll cost
    public static final BigDecimal COST_ONE = new BigDecimal(3);
    public static final BigDecimal COST_TEN = new BigDecimal(30);

    // crystal cost
    public static final BigDecimal COST_CRYSTAL = new BigDecimal(6);
}
