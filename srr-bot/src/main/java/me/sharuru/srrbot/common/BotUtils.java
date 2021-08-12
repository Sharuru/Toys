package me.sharuru.srrbot.common;

import me.sharuru.srrbot.entity.MaterialEntity;
import me.sharuru.srrbot.mapper.MaterialMapper;
import me.sharuru.srrbot.webhook.model.webhook.WebhookRequestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class BotUtils {

    @Autowired
    private MaterialMapper materialMapper;

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

    public MaterialEntity getRandomSelectedMaterial(String catalog, int materialThreshold) {
        List<MaterialEntity> materials = materialMapper.findAllByCatalogAndThreshold(catalog, materialThreshold);
        return materials.get(ThreadLocalRandom.current().nextInt(0, materials.size()));
    }

    public String fillNickname(String rawText, String nickname) {
        return rawText.replace("#nickname#", nickname);
    }
}
