package me.sharuru.srrbot.common;

import me.sharuru.srrbot.entity.MaterialEntity;
import me.sharuru.srrbot.mapper.MaterialMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 各种工具方法
 */
@Component
public class BotUtils {

    @Autowired
    private MaterialMapper materialMapper;

    /**
     * 把阿拉伯数字换换成罗马字符
     *
     * @param number 阿拉伯数字
     * @return 罗马字符
     */
    public String convertToRomanNumerals(int number) {
        switch (number) {
            case 1:
                return "I";
            case 2:
                return "II";
            case 3:
                return "III";
            case 4:
                return "IV";
            case 5:
                return "V";
            case 6:
                return "VI";
            default:
                return "?";
        }
    }

    /**
     * 随机从语料中选择一条语料纪录
     *
     * @param catalog           语料分类
     * @param materialThreshold 筛选阈值
     * @return 语料纪录
     */
    public MaterialEntity getRandomSelectedMaterial(String catalog, int materialThreshold) {
        List<MaterialEntity> materials = materialMapper.findAllByCatalogAndThreshold(catalog, materialThreshold);
        return materials.get(ThreadLocalRandom.current().nextInt(0, materials.size()));
    }

    /**
     * 将昵称填入语料模板
     *
     * @param rawText  原始文本
     * @param nickname 昵称
     * @return 填入昵称后的文本
     */
    public String fillNickname(String rawText, String nickname) {
        return rawText.replace("#nickname#", nickname);
    }
}
