package self.srr.roll;

import lombok.Data;

/**
 * roll_record 表结果映射
 * <p>
 * Created by Sharuru on 2017/5/27 0027.
 */
@Data
class RollRecord {
    // 主键
    private Integer id;
    // 用户名
    private String uid;
    // 用户昵称
    private String nicename;
    // 用户余额
    private Float amount;

}
