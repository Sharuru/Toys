package me.sharuru.srrbot.entity;

import lombok.Data;

/**
 * 语料表实体
 */
@Data
public class MaterialEntity {
    private int id;
    private String catalog;
    private String type;
    private int threshold;
    private String context;
    private int score;
}
