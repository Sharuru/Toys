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

                // 计算两种不同的请假策略
                LeaveStrategy earlyStrategy = calculateLeaveStrategyEarlyStart(arrivalTime, LocalTime.of(17, 30));
                LeaveStrategy efficientStrategy = calculateLeaveStrategyFlexibleStart(arrivalTime, LocalTime.of(18, 0));

                if (earlyStrategy.leaveHours == efficientStrategy.leaveHours) {
                    // 两种策略请假时长相同，只显示早下班的方案
                    result.append(String.format("  \\>\\> [最高优先级] 申请许可 %.1f 小时 %s，于 17:30 结束勤务。\n",
                            earlyStrategy.leaveHours, earlyStrategy.formatCoverage()));
                } else {
                    // 存在权衡，提供两个选项
                    result.append(String.format("  \\>\\> [效率路径] 申请许可 %.1f 小时 %s，于 17:30 结束勤务。(优先确保休息时间)\n",
                            earlyStrategy.leaveHours, earlyStrategy.formatCoverage()));

                    result.append(String.format("  \\>\\> [资源保全路径] 申请许可 %.1f 小时 %s，于 18:00 结束勤务。(节约许可时长)\n",
                            efficientStrategy.leaveHours, efficientStrategy.formatCoverage()));
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
     * 计算早下班策略：请假从 08:30 开始，确保能在目标时间下班
     *
     * @param arrivalTime   实际打卡时间
     * @param targetEndTime 目标下班时间（17:30）
     * @return 请假策略，包含请假时长和时段
     */
    private LeaveStrategy calculateLeaveStrategyEarlyStart(LocalTime arrivalTime, LocalTime targetEndTime) {
        double leaveHours = calculateRequiredLeaveHours(arrivalTime, targetEndTime);
        if (leaveHours == 0.0) {
            return new LeaveStrategy(0.0, STANDARD_START_TIME, STANDARD_START_TIME);
        }

        // 请假从 08:30 开始，计算请假结束时间
        long leaveMinutes = (long) (leaveHours * 60);
        LocalTime leaveEnd = STANDARD_START_TIME.plusMinutes(leaveMinutes);

        // 如果请假结束时间早于打卡时间，需要增加请假时长以覆盖到打卡时间
        if (leaveEnd.isBefore(arrivalTime)) {
            long minutesNeeded = Duration.between(STANDARD_START_TIME, arrivalTime).toMinutes();
            leaveHours = roundLeaveHours(minutesNeeded / 60.0);
            leaveMinutes = (long) (leaveHours * 60);
            leaveEnd = STANDARD_START_TIME.plusMinutes(leaveMinutes);
        }

        return new LeaveStrategy(leaveHours, STANDARD_START_TIME, leaveEnd);
    }

    /**
     * 计算资源节约策略：灵活调整请假起始时间，尽可能延后请假时段
     *
     * @param arrivalTime   实际打卡时间
     * @param targetEndTime 目标下班时间（18:00）
     * @return 请假策略，包含请假时长和时段
     */
    private LeaveStrategy calculateLeaveStrategyFlexibleStart(LocalTime arrivalTime, LocalTime targetEndTime) {
        double leaveHours = calculateRequiredLeaveHours(arrivalTime, targetEndTime);
        if (leaveHours == 0.0) {
            return new LeaveStrategy(0.0, STANDARD_START_TIME, STANDARD_START_TIME);
        }

        // 计算请假结束时间：必须覆盖到打卡时间，并向上对齐到半小时整点
        LocalTime requiredLeaveEnd = alignToHalfHourUp(arrivalTime);

        // 从结束时间往回推请假时长，得到起始时间
        long leaveMinutes = (long) (leaveHours * 60);
        LocalTime leaveStart = requiredLeaveEnd.minusMinutes(leaveMinutes);

        // 请假起始时间必须向下取整到半小时整点
        leaveStart = alignToHalfHour(leaveStart);

        // 请假起始时间不能早于标准上班时间 08:30
        if (leaveStart.isBefore(STANDARD_START_TIME)) {
            leaveStart = STANDARD_START_TIME;
        }

        // 重新计算请假结束时间
        LocalTime leaveEnd = leaveStart.plusMinutes(leaveMinutes);

        // 确保请假结束时间覆盖到打卡时间
        if (leaveEnd.isBefore(requiredLeaveEnd)) {
            leaveEnd = requiredLeaveEnd;
            // 重新计算实际请假时长
            leaveMinutes = Duration.between(leaveStart, leaveEnd).toMinutes();
            leaveHours = leaveMinutes / 60.0;
        }

        return new LeaveStrategy(leaveHours, leaveStart, leaveEnd);
    }

    /**
     * 将时间向下取整到最近的半小时整点
     * 例如：10:52 → 10:30, 10:15 → 10:00, 09:30 → 09:30
     *
     * @param time 原始时间
     * @return 对齐后的时间
     */
    private LocalTime alignToHalfHour(LocalTime time) {
        int minute = time.getMinute();
        int alignedMinute = (minute / 30) * 30; // 向下取整到 0 或 30
        return LocalTime.of(time.getHour(), alignedMinute);
    }

    /**
     * 将时间向上取整到最近的半小时整点
     * 例如：10:52 → 11:00, 10:15 → 10:30, 09:30 → 09:30
     *
     * @param time 原始时间
     * @return 对齐后的时间
     */
    private LocalTime alignToHalfHourUp(LocalTime time) {
        int minute = time.getMinute();
        if (minute == 0 || minute == 30) {
            // 已经对齐，直接返回
            return time;
        }
        // 向上取整
        int alignedMinute = ((minute + 29) / 30) * 30;
        if (alignedMinute >= 60) {
            // 需要进位到下一个小时
            return LocalTime.of(time.getHour(), 0).plusHours(1);
        }
        return LocalTime.of(time.getHour(), alignedMinute);
    }

    /**
     * 计算为了在目标时间下班所需的请假时长
     *
     * @param arrivalTime   实际打卡时间
     * @param targetEndTime 目标下班时间
     * @return 请假小时数（已应用凑整规则），无需请假时返回 0.0
     */
    private double calculateRequiredLeaveHours(LocalTime arrivalTime, LocalTime targetEndTime) {
        // 1. 计算实际在岗工作时长（扣除午休）
        Duration onSiteDuration = Duration.between(arrivalTime, targetEndTime);
        if (arrivalTime.isBefore(LUNCH_END_TIME) && targetEndTime.isAfter(LUNCH_START_TIME)) {
            onSiteDuration = onSiteDuration.minus(LUNCH_DURATION);
        }

        // 2. 计算工时缺口
        Duration shortFall = REQUIRED_WORK_DURATION.minus(onSiteDuration);
        if (shortFall.isNegative() || shortFall.isZero()) {
            return 0.0;
        }

        // 3. 将缺口时长转换为请假小时数（应用凑整规则）
        double theoreticalHours = shortFall.toMinutes() / 60.0;
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


}
