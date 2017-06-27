package self.srr.bot.biz.fish.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import self.srr.bot.base.common.BotRequestModel;
import self.srr.bot.base.common.BotResponseModel;
import self.srr.bot.base.common.BotUtils;
import self.srr.bot.biz.fish.common.FishConstant;
import self.srr.bot.biz.fish.entity.TblFishTimeRecord;
import self.srr.bot.biz.fish.repository.FishTimeRepository;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// TODO duty time setting in application.yml

/**
 * Fish service
 * <p>
 * Created by Sharuru on 2017/06/07.
 */
@Service
@Slf4j
public class FishService {

    @Autowired
    private FishTimeRepository fishTimeRepository;

    private BotRequestModel botRequestModel = new BotRequestModel();

    /**
     * Fish service flow
     *
     * @param botRequestModel request
     * @return response
     */
    public BotResponseModel start(BotRequestModel botRequestModel) {
        this.botRequestModel = botRequestModel;

        // parse command
        String[] args = BotUtils.commandParser(botRequestModel.getText());
        String action = args[0];
        BotResponseModel botResponseModel = BotUtils.getDefaultResponseModel(args);

        log.info("User '" + botRequestModel.getUser_name() + "' inputted '" + botRequestModel.getText() + "'");

        // switch action
        if ("help".equalsIgnoreCase(action)) {
            botResponseModel = helpMsgBiz(botResponseModel);
        } else if ("ci".equalsIgnoreCase(action)) {
            botResponseModel = checkInBiz(botResponseModel, args);
        } else {
            botResponseModel = etaBiz(botResponseModel);
        }

        botResponseModel.appendAtWho(botRequestModel.getUser_name());

        return botResponseModel;
    }

    /**
     * Help message business
     *
     * @param botResponseModel prev. response
     * @return response
     */
    private BotResponseModel helpMsgBiz(BotResponseModel botResponseModel) {

        String text = "" +
                "摸鱼计时的帮助信息：\n" +
                "使用方法：输入 `/fish 指令。`\n" +
                "`/fish` 显示今天还得摸多久才能跑路；\n" +
                "`/fish help` 显示本帮助信息；\n" +
                "`/fish ci 0900` 记录今天的出勤时间为上午九点，再次使用则进行修改；\n" +
                "在指令后追加 s 表示将本次响应公开；\n" +
                "**所有时间均已 +8 区为基准进行计算。**";

        botResponseModel.setText(text);

        return botResponseModel;
    }

    /**
     * Check in time register business
     *
     * @param botResponseModel prev.response
     * @param args             user command
     * @return response
     */
    private BotResponseModel checkInBiz(BotResponseModel botResponseModel, String[] args) {

        // parameter check
        // have HHmm info
        if (args.length >= 2) {
            try {    // check args is legal
                String HHmmStr = args[1];
                Date inputDate = getSystemLocalDate(null, null, null, Integer.valueOf(HHmmStr.substring(0, 2)), Integer.valueOf(HHmmStr.substring(2, 4)), 0, FishConstant.ZONE_SHANGHAI);
                try {
                    TblFishTimeRecord record = fishTimeRepository.findTodayByUserId(botRequestModel.getUser_id());
                    if (record == null) {
                        // insert
                        TblFishTimeRecord newRecord = fishTimeRepository.save(new TblFishTimeRecord(botRequestModel.getUser_id(), botRequestModel.getUser_name(), inputDate));
                        botResponseModel.setText("出勤时间已记录为：" + HHmmStr + "。");
                        log.info("Record inserted: " + newRecord.toString());
                    } else {
                        // update
                        record.setCheckInTime(inputDate);
                        fishTimeRepository.save(record);
                        botResponseModel.setText("出勤时间已更新为：" + HHmmStr + "。");
                        log.info("Record updated: " + record.toString());
                    }
                } catch (Exception e) {
                    botResponseModel.setText("似乎发生了奇怪的问题，麻烦稍后再试。");
                    log.error("Error happened in 'checkInBiz': " + e.getMessage());
                    e.printStackTrace();
                }
            } catch (Exception e) {
                botResponseModel.setText("时间格式似乎输入错误了哟（HHmm）。");
                log.error("Error happened in 'checkInBiz': " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            // no args, using default
            fishTimeRepository.save(new TblFishTimeRecord(botRequestModel.getUser_id(), botRequestModel.getUser_name(), getSystemLocalDate(null, null, null, 9, 0, 0, FishConstant.ZONE_SHANGHAI)));
            botResponseModel.setText("未指定出勤时间，已自动记录为： 0900。");
        }

        return botResponseModel;
    }

    /**
     * Calculate eta duty time
     *
     * @param botResponseModel prev.response
     * @return response
     */
    private BotResponseModel etaBiz(BotResponseModel botResponseModel) {
        // find record
        TblFishTimeRecord record = fishTimeRepository.findTodayByUserId(botRequestModel.getUser_id());
        if (record == null) {
            // no record
            botResponseModel.setText("今日出勤时间未记录（使用 `/fish ci HHmm` 来记录）。");
        } else {
            // calculate
            Map<String, BigDecimal> etaMap = etaCalculator(record.getCheckInTime());
            // decorate
            String text = "";

            if (etaMap.get(FishConstant.KEY_SECOND).compareTo(BigDecimal.ZERO) == 1) {
                text += "哎呀，还得摸：" + etaMap.get(FishConstant.KEY_SECOND).abs() + " 秒";
            } else {
                text += "嚯哟！你给自己续了：" + etaMap.get(FishConstant.KEY_SECOND).abs() + " 秒";
            }
            if (etaMap.get(FishConstant.KEY_SECOND).divide(new BigDecimal(60L), 2, BigDecimal.ROUND_HALF_UP).compareTo(BigDecimal.ONE) == 1) {
                text += "，约 " + etaMap.get(FishConstant.KEY_MINUTE).abs() + " 分钟";
            }
            if (etaMap.get(FishConstant.KEY_SECOND).divide(new BigDecimal(3600L), 2, BigDecimal.ROUND_HALF_UP).compareTo(BigDecimal.ONE) == 1) {
                text += "，约 " + etaMap.get(FishConstant.KEY_HOUR).abs() + " 小时";
            }
            text += "。";

            botResponseModel.setText(text);
        }
        return botResponseModel;
    }


    /**
     * Get time at system default timezone
     *
     * @param year   year
     * @param month  month
     * @param day    day
     * @param hour   hour
     * @param minute minute
     * @param second second
     * @param zoneId zone id(default: Asia/Shanghai +0800)
     * @return date time at system default timezone
     */
    private Date getSystemLocalDate(Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer second, ZoneId zoneId) {
        ZonedDateTime now = ZonedDateTime.now(FishConstant.ZONE_SHANGHAI);
        int zYear = year == null ? now.getYear() : year;
        int zMonth = month == null ? now.getMonthValue() : month;
        int zDay = day == null ? now.getDayOfMonth() : day;
        int zHour = hour == null ? now.getHour() : hour;
        int zMinute = minute == null ? now.getMinute() : minute;
        int zSecond = second == null ? now.getSecond() : second;
        ZoneId zZoneId = zoneId == null ? now.getZone() : zoneId;

        ZonedDateTime record = ZonedDateTime.of(zYear, zMonth, zDay, zHour, zMinute, zSecond, 0, zZoneId);
        return Date.from(Instant.from(record.toInstant().atZone(ZoneId.systemDefault())));
    }

    /**
     * Calculate eta duty time
     * <p>
     * rules:
     * if CHECK_IN < 0900 OR > 0931, END = 1800
     * else END = 1800 + duration between CHECK_IN and BUFFER_END
     *
     * @param recordDatetime record time
     * @return eta duty map
     */
    private Map<String, BigDecimal> etaCalculator(Date recordDatetime) {
        ZonedDateTime checkInDateTime = ZonedDateTime.ofInstant(recordDatetime.toInstant(), FishConstant.ZONE_SHANGHAI);
        ZonedDateTime startDateTime = ZonedDateTime.of(checkInDateTime.getYear(), checkInDateTime.getMonthValue(), checkInDateTime.getDayOfMonth(), 9, 0, 0, 0, FishConstant.ZONE_SHANGHAI);
        ZonedDateTime bufferDateTime = startDateTime.plusMinutes(31L);
        ZonedDateTime endDateTime = startDateTime.plusHours(9L);

        if (checkInDateTime.isAfter(startDateTime) && checkInDateTime.isBefore(bufferDateTime)) {
            endDateTime = endDateTime.plusSeconds(Duration.between(startDateTime, checkInDateTime).getSeconds());
        }

        Duration duration = Duration.between(ZonedDateTime.now(FishConstant.ZONE_SHANGHAI), endDateTime);

        Map<String, BigDecimal> retMap = new HashMap<>();
        retMap.put(FishConstant.KEY_SECOND, (new BigDecimal(duration.getSeconds())));
        retMap.put(FishConstant.KEY_MINUTE, (new BigDecimal(duration.getSeconds()).divide(new BigDecimal(60L), 2, BigDecimal.ROUND_HALF_UP)));
        retMap.put(FishConstant.KEY_HOUR, (new BigDecimal(duration.getSeconds()).divide(new BigDecimal(3600L), 2, BigDecimal.ROUND_HALF_UP)));

        return retMap;
    }

}
