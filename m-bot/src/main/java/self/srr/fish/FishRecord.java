package self.srr.fish;

/**
 * Created by Sharuru on 2017/4/29 0029.
 */
public class FishRecord {

    Integer id;
    String user;
    Integer check_time;
    Integer input_time;

    public Integer getId() {
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
