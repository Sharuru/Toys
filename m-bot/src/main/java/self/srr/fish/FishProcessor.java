package self.srr.fish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import self.srr.common.BotRequestModel;
import self.srr.common.BotResponseModel;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 摸鱼计时的功能实现
 * <p>
 * Created by Sharuru on 2017/04/28.
 */
@Service
class FishProcessor {

    @Autowired
    private FishMapper mapper;

    private BotResponseModel botResp = new BotResponseModel();
    private BotRequestModel botReq = new BotRequestModel();

    // 主处理
    BotResponseModel main(BotRequestModel botReq) {
        this.botReq = botReq;
        // 解析参数以执行不同的行为
        String[] userCommand = botReq.getText().split("\\s+");
        // 选择行为
        if ("help".equalsIgnoreCase(userCommand[0])) {
            // 显示帮助信息
            showHelpMsg();
        } else if ("ci".equalsIgnoreCase(userCommand[0])) {
            // 摸鱼计时登记
            if (userCommand.length >= 2) {
                regTime(userCommand[1]);
            } else {
                // 缺失参数
                botResp.setText("参数忘记输入了的样子……使用 `/fish help` 来获取帮助。");
            }
        } else {
            // 根据时刻计算剩余时间
            chkTime();
        }
        return botResp;
    }

    /**
     * 显示帮助信息
     */
    private void showHelpMsg() {
        String text = "" +
                "摸鱼计时的帮助信息：\n" +
                "使用方法：输入 `/fish 指令。`\n" +
                "`/fish` 显示今天还得摸多久才能跑路；\n" +
                "`/fish help` 显示本帮助信息；\n" +
                "`/fish ci 0900` 记录今天的出勤时间为上午九点，再次使用则进行修改；\n" +
                "**所有时间均已 +8 区为基准进行计算。**";
        botResp.setText(text);
    }

    /**
     * 登记或修改今日出勤时间
     *
     * @param timeStr HHmm 格式的出勤时间
     */
    private void regTime(String timeStr) {
        ZonedDateTime now = ZonedDateTime.now(FishContrast.ZONE_SHANGHAI);
        ZonedDateTime inputTzTime;
        // 检测是否是时间格式
        try {
            // 尝试将用户输入转换成对应时间
            inputTzTime = ZonedDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), Integer.valueOf(timeStr.substring(0, 2)), Integer.valueOf(timeStr.substring(2, 4)), 0, 0, FishContrast.ZONE_SHANGHAI);
            try {
                // 转换成对应的形式更新数据库
                ZonedDateTime todayBegin = ZonedDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 0, 0, 0, 0, FishContrast.ZONE_SHANGHAI);

                // 转换成时间戳
                Long beginTs = Date.from(todayBegin.toInstant()).getTime() / 1000;      // 今日开始
                Long endTs = beginTs + FishContrast.ONE_DAY;   // 今日结束
                Long workTs = beginTs + FishContrast.NINE_HOUR;  // 上班时间（上午九点）
                Long inputTs = Date.from(inputTzTime.toInstant()).getTime() / 1000;     // 用户输入

                // 查询数据库已有记录
                FishRecord dbRecord = mapper.findOneByTime(botReq.getUser_name(), beginTs, endTs);

                // 已存在记录，更新
                if (dbRecord != null) {
                    // 今日已登记过，修改记录
                    if (inputTs > workTs + FishContrast.HALF_HOUR) {
                        // 超过弹性时间（上午九点半）
                        mapper.updateTimeById(dbRecord.getId(), workTs + FishContrast.NINE_HOUR, inputTs);
                        botResp.setText("人心散了，队伍带不动了啊……出勤时间已修改为：09:00。（超出弹性时间）");
                    } else {
                        mapper.updateTimeById(dbRecord.getId(), inputTs + FishContrast.NINE_HOUR, inputTs);
                        botResp.setText("今天的出勤时间已经修改成：" + inputTzTime.format(DateTimeFormatter.ofPattern("HH:mm")) + " 了哦！");
                    }
                } else {
                    // 插入数据库
                    if (inputTs > workTs + FishContrast.HALF_HOUR) {
                        // 超过弹性时间（上午九点半）
                        mapper.insertTime(botReq.getUser_name(), workTs + FishContrast.NINE_HOUR, inputTs);
                        botResp.setText("真鸡儿丢人！都超过弹性时间了，这大清药丸啊！出勤时间已修改为：09:00。（超出弹性时间）");
                    } else {
                        mapper.insertTime(botReq.getUser_name(), inputTs + FishContrast.NINE_HOUR, inputTs);
                        botResp.setText("今天的出勤时间已经记录为：" + inputTzTime.format(DateTimeFormatter.ofPattern("HH:mm")) + " 了哟！今日も一日頑張るぞい！");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                botResp.setText("似乎发生了奇怪的问题，麻烦稍后再试。");
            }
        } catch (Exception e) {
            botResp.setText("时间格式好像输入错误了哟。");
        }
    }

    /**
     * 计算剩余时间
     */
    private void chkTime() {
        // 获取时间戳
        ZonedDateTime now = ZonedDateTime.now(FishContrast.ZONE_SHANGHAI);
        ZonedDateTime todayBegin = ZonedDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 0, 0, 0, 0, FishContrast.ZONE_SHANGHAI);
        Long beginTs = Date.from(todayBegin.toInstant()).getTime() / 1000;      // 今日开始
        Long endTs = beginTs + FishContrast.ONE_DAY;   // 今日结束

        // 查询数据库已有记录
        FishRecord dbRecord = mapper.findOneByTime(botReq.getUser_name(), beginTs, endTs);

        // 不存在记录
        if (dbRecord == null) {
            botResp.setText("貌似今天还没有记录出勤时间哦！（使用 `/fish ci HHmm` 来记录）");
        } else {
            // 计算
            ZonedDateTime end = Instant.ofEpochSecond(dbRecord.getCheck_time()).atZone(FishContrast.ZONE_SHANGHAI);
            Duration duration = Duration.between(now, end);
            Long etaSeconds = duration.getSeconds();
            Long etaSecondsAbs = Math.abs(etaSeconds);
            String context = "";
            if (etaSeconds > 0) {
                // 剩余时间
                context += "哎呀，还得摸：" + etaSeconds + " 秒";
            } else {
                // 续命时间
                context += "嚯哟！你给自己续了：" + etaSecondsAbs + " 秒";
            }
            if (etaSecondsAbs > 60 && etaSecondsAbs < 3600) {
                context += "，约 " + (etaSecondsAbs / 60) + " 分钟";
            }
            if (etaSecondsAbs > 3600) {
                context += "，约 " + new BigDecimal(etaSecondsAbs).divide(new BigDecimal(3600), 2,BigDecimal.ROUND_HALF_UP) + " 小时";
            }
            botResp.setText(context);
        }
    }
}
