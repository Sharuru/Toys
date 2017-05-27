package self.srr.fish;

import lombok.Data;

/**
 * fish_record 表结果映射
 * <p>
 * Created by Sharuru on 2017/4/29 0029.
 */
@Data
class FishRecord {

    // 主键
    private Integer id;
    // 用户名
    private String user;
    // 检查用时间戳
    private Integer check_time;
    // 用户输入时间戳
    private Integer input_time;

}
