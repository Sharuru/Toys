package self.srr.roll;

import lombok.Data;

/**
 * 充值后响应 response
 * <p>
 * Created by Sharuru on 2017/5/27 0027.
 */
@Data
public class RollResponse {

    String uid;
    String amount;
    String stone;
    String chargeAmount;
    String freeAmount;
}
