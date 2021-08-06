package me.sharuru.srrbot.entity;

import lombok.Data;

@Data
public class MaterialEntity {
    private int id;
    private String catalog;
    private String type;
    private int threshold;
    private String context;
    private int score;
}
