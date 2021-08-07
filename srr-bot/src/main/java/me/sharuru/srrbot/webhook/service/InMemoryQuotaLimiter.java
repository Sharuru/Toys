package me.sharuru.srrbot.webhook.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class InMemoryQuotaLimiter {

    private List<QuotaInfo> quotaInfos = new ArrayList<>(0);
    private final int dailyLimit = 5;

    public boolean isLimited(String number, String type) {
        QuotaInfo quota = this.quotaInfos.stream().filter(q ->
                number.equals(q.getNumber()) && type.equals(q.getType())
        ).findFirst().orElse(null);

        if (quota != null) {
            LocalDateTime nowTime = LocalDateTime.now();
            if (Duration.between(nowTime, quota.getQuotaBase()).toHours() >= 24) {
                quota.setQuotaBase(nowTime);
                quota.setCallTimes(1);
            } else if (quota.getCallTimes() >= dailyLimit) {
                return true;
            } else {
                quota.setCallTimes(quota.getCallTimes() + 1);
                return false;
            }
        } else {
            quota = new QuotaInfo();
            quota.setNumber(number);
            quota.setType(type);
            quota.setQuotaBase(LocalDateTime.now());
            quota.setCallTimes(1);
            quotaInfos.add(quota);
            return false;
        }

        return true;
    }

}

@Data
class QuotaInfo {
    private String number;
    private String type;
    private LocalDateTime quotaBase;
    private int callTimes;
}
