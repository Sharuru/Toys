package me.sharuru.srrbot.entity;

import lombok.Data;

/**
 * 用户表实体
 */
@Data
public class UserEntity {
    private int id;
    private String number;
    private int score;
    private int potential;
}
