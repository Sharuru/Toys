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

    /**
     * 请假策略内部类，包含请假时长和时段信息
     */
    private static class LeaveStrategy {
        final double leaveHours;
        final LocalTime leaveStart;
        final LocalTime leaveEnd;

        LeaveStrategy(double leaveHours, LocalTime leaveStart, LocalTime leaveEnd) {
            this.leaveHours = leaveHours;
            this.leaveStart = leaveStart;
            this.leaveEnd = leaveEnd;
        }

        String formatCoverage() {
            return String.format("(覆盖 %s-%s)",
                    leaveStart.format(OUTPUT_FORMATTER),
                    leaveEnd.format(OUTPUT_FORMATTER));
        }
    }

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
                // 早于标准上班时间的，按标准时间 08:30 计算
                LocalTime effectiveStartTime = arrivalTime.isBefore(STANDARD_START_TIME) ? STANDARD_START_TIME : arrivalTime;
                LocalTime endTime = effectiveStartTime.plus(REQUIRED_WORK_DURATION).plus(LUNCH_DURATION);
                return "勤务排程正常。预计结束时间：" + endTime.format(OUTPUT_FORMATTER) + "。";
            }
            // 情况二：迟到时段打卡 (> 09:30)
            else {
                StringBuilder result = new StringBuilder();
                result.append("检测到勤务排程偏移，正在为您生成应急预案...\n\n");
                // --- 策略一：请假补齐 ---
                result.append("[应急预案 I: "时间校正"协议]\n");
                result.append("通过申请离岗许可，弥补勤务时长缺口。\n");
                LeaveStrategy strategyFor1730 = calculateLeaveStrategy(arrivalTime, LocalTime.of(17, 30));
                LeaveStrategy strategyFor1800 = calculateLeaveStrategy(arrivalTime, LocalTime.of(18, 0));
                if (strategyFor1730.leaveHours == strategyFor1800.leaveHours) {
                    // 智能推荐：成本相同，选最早下班的
                    result.append(String.format("  \\>\\> [最高优先级] 申请许可 %.1f 小时 %s，于 17:30 结束勤务。\n",
                            strategyFor1730.leaveHours, strategyFor1730.formatCoverage()));
                } else {
                    // 存在权衡，提供两个选项
                    result.append(String.format("  \\>\\> [效率路径] 申请许可 %.1f 小时 %s，于 17:30 结束勤务。(优先确保休息时间)\n",
                            strategyFor1730.leaveHours, strategyFor1730.formatCoverage()));

                    result.append(String.format("  \\>\\> [资源保全路径] 申请许可 %.1f 小时 %s，于 18:00 结束勤务。(节约许可时长)\n",
                            strategyFor1800.leaveHours, strategyFor1800.formatCoverage()));
                }
                result.append("\n"); // 增加空行，使格式更清晰
                // --- 策略二：工时顺延 ---
                result.append("[应急预案 II: "勤务平移"协议]\n");
                result.append("将勤务时段整体向后平移，以确保总时长达标。\n");
                LocalTime lateEndTime = arrivalTime.plus(REQUIRED_WORK_DURATION).plus(LUNCH_DURATION);
                result.append("  \\>\\> 预计结束时间：").append(lateEndTime.format(OUTPUT_FORMATTER))
                        .append("\n  [警告: 此协议将记录为一次'勤务排程偏移'事件，具体影响请参照人事部最新条例。]");
                return result.toString();
            }
        } catch (DateTimeParseException e) {
            return "[错误] :: 时间戳格式无法解析。请输入有效的4位数字时间，例如 '0945'。";
        }
    }

    /**
     * 计算为了在某个目标时间下班的请假策略
     *
     * @param arrivalTime   实际打卡时间
     * @param targetEndTime 目标下班时间
     * @return 请假策略，包含请假时长和时段
     */
    private LeaveStrategy calculateLeaveStrategy(LocalTime arrivalTime, LocalTime targetEndTime) {
        // 1. 计算实际在岗工作时长（扣除午休）
        Duration onSiteDuration = Duration.between(arrivalTime, targetEndTime);
        if (arrivalTime.isBefore(LUNCH_END_TIME) && targetEndTime.isAfter(LUNCH_START_TIME)) {
            onSiteDuration = onSiteDuration.minus(LUNCH_DURATION);
        }

        // 2. 计算工时缺口
        Duration shortFall = REQUIRED_WORK_DURATION.minus(onSiteDuration);
        if (shortFall.isNegative() || shortFall.isZero()) {
            // 无需请假
            return new LeaveStrategy(0.0, STANDARD_START_TIME, STANDARD_START_TIME);
        }

        // 3. 将缺口时长转换为请假小时数（应用凑整规则）
        double theoreticalHours = shortFall.toMinutes() / 60.0;
        double leaveHours = roundLeaveHours(theoreticalHours);

        // 4. 计算请假时段，确保请假结束时间不早于实际打卡时间
        long leaveMinutes = (long) (leaveHours * 60);
        LocalTime leaveEnd = STANDARD_START_TIME.plusMinutes(leaveMinutes);

        // 如果请假结束时间早于打卡时间，需要增加请假时长以覆盖到打卡时间
        if (leaveEnd.isBefore(arrivalTime)) {
            // 计算从标准时间到打卡时间需要多少分钟
            long minutesNeeded = Duration.between(STANDARD_START_TIME, arrivalTime).toMinutes();
            double hoursNeeded = minutesNeeded / 60.0;
            // 应用凑整规则
            leaveHours = roundLeaveHours(hoursNeeded);
            leaveMinutes = (long) (leaveHours * 60);
            leaveEnd = STANDARD_START_TIME.plusMinutes(leaveMinutes);
        }

        // 请假从标准上班时间开始
        return new LeaveStrategy(leaveHours, STANDARD_START_TIME, leaveEnd);
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


}
