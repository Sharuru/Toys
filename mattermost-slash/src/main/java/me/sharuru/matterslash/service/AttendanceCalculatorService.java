package me.sharuru.matterslash.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Slf4j
@Service
public class AttendanceCalculatorService {

    // --- 核心考勤规则常量 ---
    private static final LocalTime STANDARD_START_TIME = LocalTime.of(8, 30);
    private static final LocalTime NORMAL_PERIOD_END_TIME = LocalTime.of(9, 30);
    private static final LocalTime LUNCH_START_TIME = LocalTime.of(11, 30);
    private static final LocalTime LUNCH_END_TIME = LocalTime.of(12, 30);
    private static final Duration REQUIRED_WORK_DURATION = Duration.ofHours(8);
    private static final Duration LUNCH_DURATION = Duration.ofHours(1);
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("HHmm");
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private static final String HELP_MESSAGE = """
            出勤时间计算的帮助信息：
            
            【使用方法】
            输入 `/at 你的打卡时间（4位数字）`。
            """;

    public String payload(final String userInput) {
        if("help".equals(userInput)){
            return HELP_MESSAGE;
        } else {
            return calculate(userInput);
        }
    }

    /**
     * 根据打卡时间计算下班策略
     *
     * @param arrivalTimeStr 4位数字的打卡时间字符串，例如 "0945"
     * @return 包含下班策略的格式化字符串
     */
    public String calculate(String arrivalTimeStr) {
        try {
            LocalTime arrivalTime = LocalTime.parse(arrivalTimeStr, INPUT_FORMATTER);
            // 情况一：正常时段打卡 (<= 09:30)
            if (!arrivalTime.isAfter(NORMAL_PERIOD_END_TIME)) {
                LocalTime endTime = arrivalTime.plus(REQUIRED_WORK_DURATION).plus(LUNCH_DURATION);
                return "正常打卡，" + endTime.format(OUTPUT_FORMATTER) + " 下班";
            }
            // 情况二：迟到时段打卡 (> 09:30)
            else {
                StringBuilder result = new StringBuilder();
                // --- 策略一：请假补齐 ---
                result.append("策略一：请假补齐\n");
                double leaveHoursFor1730 = calculateRequiredLeaveHours(arrivalTime, LocalTime.of(17, 30));
                double leaveHoursFor1800 = calculateRequiredLeaveHours(arrivalTime, LocalTime.of(18, 0));
                if (leaveHoursFor1730 == leaveHoursFor1800) {
                    // 智能推荐：成本相同，选最早下班的
                    String coverage = formatLeaveCoverage(leaveHoursFor1730);
                    result.append(String.format("最优方案：请假 %.1f 小时 %s，17:30 下班。\n", leaveHoursFor1730, coverage));
                } else {
                    // 存在权衡，提供两个选项
                    String coverageA = formatLeaveCoverage(leaveHoursFor1730);
                    result.append(String.format("方案A: 请假 %.1f 小时 %s，17:30 下班。\n", leaveHoursFor1730, coverageA));

                    String coverageB = formatLeaveCoverage(leaveHoursFor1800);
                    result.append(String.format("方案B: 请假 %.1f 小时 %s，18:00 下班。\n", leaveHoursFor1800, coverageB));
                }
                result.append("\n"); // 增加空行，使格式更清晰
                // --- 策略二：工时顺延 ---
                result.append("策略二：工时顺延\n");
                LocalTime lateEndTime = arrivalTime.plus(REQUIRED_WORK_DURATION).plus(LUNCH_DURATION);
                result.append("下班时间：").append(lateEndTime.format(OUTPUT_FORMATTER))
                        .append(" (此方案可能产生迟到记录)");
                return result.toString();
            }
        } catch (DateTimeParseException e) {
            return "输入格式错误，请输入4位数字时间，例如 '0945'。";
        }
    }

    /**
     * 计算为了在某个目标时间下班，理论上需要请多久的假
     *
     * @param arrivalTime   打卡时间
     * @param targetEndTime 目标下班时间
     * @return 理论请假小时数 (浮点数)
     */
    private double calculateRequiredLeaveHours(LocalTime arrivalTime, LocalTime targetEndTime) {
        // 计算实际在岗时长（需要考虑午休）
        Duration onSiteDuration = Duration.between(arrivalTime, targetEndTime);
        if (arrivalTime.isBefore(LUNCH_END_TIME) && targetEndTime.isAfter(LUNCH_START_TIME)) {
            onSiteDuration = onSiteDuration.minus(LUNCH_DURATION);
        }
        // 计算工时缺口
        Duration shortFall = REQUIRED_WORK_DURATION.minus(onSiteDuration);
        // 如果没有缺口，则无需请假
        if (shortFall.isNegative() || shortFall.isZero()) {
            return 0.0;
        }
        // 将缺口时长（分钟）转换为小时
        double theoreticalHours = shortFall.toMinutes() / 60.0;
        // 应用请假凑整规则
        return roundLeaveHours(theoreticalHours);
    }

    /**
     * 应用复杂的请假凑整规则
     *
     * @param theoreticalHours 理论计算出的请假小时数
     * @return 符合规则的最终请假小时数
     */
    private double roundLeaveHours(double theoreticalHours) {
        // 规则：不足2小时的，按2小时算
        if (theoreticalHours > 0 && theoreticalHours <= 2.0) {
            return 2.0;
        }
        // 规则：超过2小时的，向上凑到最近的0.5小时
        // Math.ceil(2.2 * 2) / 2.0 = Math.ceil(4.4) / 2.0 = 5.0 / 2.0 = 2.5
        return Math.ceil(theoreticalHours * 2) / 2.0;
    }

    /**
     * 格式化请假覆盖时间段的字符串
     *
     * @param leaveHours 请假小时数
     * @return 格式如 "(覆盖 08:30-10:30)" 的字符串
     */
    private String formatLeaveCoverage(double leaveHours) {
        long leaveMinutes = (long) (leaveHours * 60);
        LocalTime coverageEnd = STANDARD_START_TIME.plusMinutes(leaveMinutes);
        return String.format("(覆盖 %s-%s)",
                STANDARD_START_TIME.format(OUTPUT_FORMATTER),
                coverageEnd.format(OUTPUT_FORMATTER));
    }

}
