package me.sharuru.mattermost.sum.model;

import lombok.Data;

@Data
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
