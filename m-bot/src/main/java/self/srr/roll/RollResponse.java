package self.srr.roll;

import lombok.Data;

/**
 * 充值后响应 response
 * <p>
 * Created by Sharuru on 2017/5/27 0027.
 */
@Data
public class RollResponse {

    // 用户 uid
    String uid;
    // 用户余额
    String amount;
    // 水晶余额
    String stone;
    // 充值金额
    String chargeAmount;
    // 系统剩余余额
    String freeAmount;
}
