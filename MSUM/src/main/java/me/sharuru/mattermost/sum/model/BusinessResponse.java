package me.sharuru.mattermost.sum.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class BusinessResponse extends CreateUserForm {

    private boolean status;

    private int code = -1;

    public BusinessResponse(boolean status) {
        this.status = status;
    }

    public BusinessResponse(boolean status, int code) {
        this.status = status;
        this.code = code;
    }
}
