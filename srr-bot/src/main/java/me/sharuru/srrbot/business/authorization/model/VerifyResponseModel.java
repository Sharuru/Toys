package me.sharuru.srrbot.business.authorization.model;

import lombok.Data;

@Data
public class VerifyResponseModel {

    private int code;
    private String msg;

}
