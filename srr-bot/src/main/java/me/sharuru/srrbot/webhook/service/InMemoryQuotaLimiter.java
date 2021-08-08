package me.sharuru.srrbot.webhook.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class InMemoryQuotaLimiter {

    @Value("${srrBot.masterQQ}")
    private String masterQQ;

    private List<QuotaInfo> quotaInfos = new ArrayList<>(0);
    private final int dailyLimit = 8;

    public boolean isLimited(String number, String type) {

        if (masterQQ.equals(number)) {
            return false;
        }

        QuotaInfo quota = this.quotaInfos.stream().filter(q ->
                number.equals(q.getNumber()) && type.equals(q.getType())
        ).findFirst().orElse(null);

        if (quota != null) {
            LocalDateTime nowTime = LocalDateTime.now();
            if (Duration.between(quota.getQuotaBase(), nowTime).toHours() >= 24) {
                quota.setQuotaBase(nowTime);
                quota.setLastCallTime(nowTime);
                quota.setCallTimes(1);
                return false;
            } else if (Duration.between(quota.getLastCallTime(), nowTime).toMinutes() <= 30) {
                return true;
            } else if (quota.getCallTimes() >= dailyLimit) {
                return true;
            } else {
                quota.setLastCallTime(nowTime);
                quota.setCallTimes(quota.getCallTimes() + 1);
                return false;
            }
        } else {
            quota = new QuotaInfo();
            quota.setNumber(number);
            quota.setType(type);
            quota.setQuotaBase(LocalDateTime.now());
            quota.setLastCallTime(quota.getQuotaBase());
            quota.setCallTimes(1);
            quotaInfos.add(quota);
            return false;
        }
    }

}

@Data
class QuotaInfo {
    private String number;
    private String type;
    private LocalDateTime quotaBase;
    private LocalDateTime lastCallTime;
    private int callTimes;
}
