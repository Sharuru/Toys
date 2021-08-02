package me.sharuru.srrbot.business.authorization.model;

import lombok.Data;

@Data
public class VerifyRequestModel {

    private String sessionKey;
    private String qq;
}
