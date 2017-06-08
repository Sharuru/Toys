package self.srr.bot.biz.fish.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import self.srr.bot.base.common.BotRequestModel;
import self.srr.bot.base.common.BotResponseModel;
import self.srr.bot.base.common.BotUtils;
import self.srr.bot.biz.fish.common.FishContrast;
import self.srr.bot.biz.fish.entity.TblFishTimeRecord;
import self.srr.bot.biz.fish.repository.FishRepository;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Fish service
 * <p>
 * Created by Sharuru on 2017/06/07.
 */
@Service
@Slf4j
public class FishService {

    @Autowired
    private FishRepository fishRepository;

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

        }

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
     * @param responseModel prev.response
     * @param args          user command
     * @return response
     */
    private BotResponseModel checkInBiz(BotResponseModel responseModel, String[] args) {
        // parameter check
        if (args.length >= 2) {       // have HHmm info
            try {    // check args is legal
                String HHmmStr = args[1];
                Date inputDate = getSystemLocalDate(null, null, null, Integer.valueOf(HHmmStr.substring(0, 2)), Integer.valueOf(HHmmStr.substring(2, 4)), 0, FishContrast.ZONE_SHANGHAI);
                try {
                    TblFishTimeRecord record = fishRepository.save(new TblFishTimeRecord(botRequestModel.getUser_name(), inputDate));
                    log.info("Record inserted: " + record.toString());
                } catch (Exception e) {
                    responseModel.setText("似乎发生了奇怪的问题，麻烦稍后再试。");
                    log.error("Error happened in 'checkInBiz': " + e.getMessage());
                    e.printStackTrace();
                }
            } catch (Exception e) {
                responseModel.setText("时间格式似乎输入错误了哟（HHmm）。");
                log.error("Error happened in 'checkInBiz': " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            fishRepository.save(new TblFishTimeRecord(botRequestModel.getUser_name(), getSystemLocalDate(null, null, null, 9, 0, 0, FishContrast.ZONE_SHANGHAI)));
            responseModel.setText("未指定出勤时间，已自动记录为： 0900。");
        }

        return responseModel;
    }


    private Date getSystemLocalDate(Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer second, ZoneId zoneId) {
        ZonedDateTime now = ZonedDateTime.now(FishContrast.ZONE_SHANGHAI);
        int zYear = year == null ? now.getYear() : year;
        int zMonth = month == null ? now.getDayOfMonth() : month;
        int zDay = day == null ? now.getDayOfMonth() : day;
        int zHour = hour == null ? now.getHour() : hour;
        int zMinute = minute == null ? now.getMinute() : minute;
        int zSecond = second == null ? now.getSecond() : second;
        ZoneId zZoneId = zoneId == null ? FishContrast.ZONE_SHANGHAI : zoneId;

        ZonedDateTime record = ZonedDateTime.of(zYear, zMonth, zDay, zHour, zMinute, zSecond, 0, zZoneId);
        return Date.from(Instant.from(record.toInstant().atZone(ZoneId.systemDefault())));
    }

}
