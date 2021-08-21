package me.sharuru.srrbot.common;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * 简单的互动冷却控制器
 */
@Slf4j
@Component
public class InMemoryQuotaLimiter {

    @Value("${srrBot.masterQQ}")
    private String masterQQ;

    private List<QuotaInfo> quotaInfos = new ArrayList<>(0);
    private static final int DAILY_LIMIT = 8;

    /**
     * 判断是否在冷却状态
     *
     * @param number 互动QQ号
     * @param type   互动类型
     * @return 冷却状态
     */
    public boolean isLimited(String number, String type) {
        return this.isLimited(number, type, 30);
    }

    /**
     * 判断是否在冷却状态
     *
     * @param number          互动QQ号
     * @param type            互动类型
     * @param coolDownMinutes 判断阈值
     * @return 冷却状态
     */
    public boolean isLimited(String number, String type, int coolDownMinutes) {

        if (masterQQ.equals(number)) {
            return false;
        }

        QuotaInfo quota = this.quotaInfos.stream().filter(q ->
                number.equals(q.getNumber()) && type.equals(q.getType())
        ).findFirst().orElse(null);

        LocalDateTime currentDateTime = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
        int todayCoolDownBase = currentDateTime.getDayOfMonth();

        if (quota != null) {
            if (quota.getCoolDownBase() != todayCoolDownBase) {  // 日期不一致，重置限制
                quota.setCoolDownBase(todayCoolDownBase);
                quota.setLastCallTime(currentDateTime);
                quota.setCallTimes(1);
                return false;
            } else if (Duration.between(quota.getLastCallTime(), currentDateTime).toMinutes() <= coolDownMinutes) {    // 互动间冷却时间
                return true;
            } else if (quota.getCallTimes() >= DAILY_LIMIT) {    // 单日最大互动次数
                return true;
            } else {
                quota.setLastCallTime(currentDateTime);
                quota.setCallTimes(quota.getCallTimes() + 1);   // 正常互动
                return false;
            }
        } else {
            // 新建冷却实体
            quota = new QuotaInfo();
            quota.setNumber(number);
            quota.setType(type);
            quota.setCoolDownBase(todayCoolDownBase);
            quota.setLastCallTime(currentDateTime);
            quota.setCallTimes(1);
            quotaInfos.add(quota);
            return false;
        }
    }

}

/**
 * 互动冷却信息对象
 */
@Data
class QuotaInfo {
    private String number;
    private String type;
    private int coolDownBase;
    private LocalDateTime lastCallTime;
    private int callTimes;
}
