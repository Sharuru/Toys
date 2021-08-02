package me.sharuru.srrbot.business.authorization.model;

import lombok.Data;

@Data
public class AuthResponseModel {

    private int code;
    private String session;

}
