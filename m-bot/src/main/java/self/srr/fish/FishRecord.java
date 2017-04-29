package self.srr.fish;

/**
 * fish_record 表结果映射
 * <p>
 * Created by Sharuru on 2017/4/29 0029.
 */
public class FishRecord {

    // 主键
    private Integer id;
    // 用户名
    private String user;
    // 检查用时间戳
    private Integer check_time;
    // 用户输入时间戳
    private Integer input_time;

    Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Integer getCheck_time() {
        return check_time;
    }

    public void setCheck_time(Integer check_time) {
        this.check_time = check_time;
    }

    public Integer getInput_time() {
        return input_time;
    }

    public void setInput_time(Integer input_time) {
        this.input_time = input_time;
    }
}
