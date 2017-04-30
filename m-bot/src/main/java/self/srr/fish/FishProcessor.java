package self.srr.fish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import self.srr.common.BotRequestModel;
import self.srr.common.BotResponseModel;

import java.sql.Date;
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
        if ("help".equals(userCommand[0].toLowerCase())) {
            // 显示帮助信息
            showHelpMsg();
        } else if ("ci".equals(userCommand[0].toLowerCase())) {
            // 摸鱼计时登记
            if (userCommand[1] != null) {
                regTime(userCommand[1]);
            }
        } else {
            // 根据时刻计算剩余时间
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
                "`/fish` 显示今天还得摸多久才能跑路；" +
                "`/fish help` 显示本帮助信息；\n" +
                "`/fish ci 0900 记录今天的出勤时间为上午九点，再次使用则进行修改；" +
                "* 所有时间均已 +8 区为基准进行计算。 *";
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
                        mapper.updateTimeById(dbRecord.getId(), workTs, inputTs);
                        botResp.setText("人心散了，队伍带不动了啊……（出勤时间已修改为：09:00）");
                    } else {
                        mapper.updateTimeById(dbRecord.getId(), inputTs, inputTs);
                        botResp.setText("今天的出勤时间已经修改成：" + inputTzTime.format(DateTimeFormatter.ofPattern("HH:mm")) + " 了哦！");
                    }
                } else {
                    // 插入数据库
                    if (inputTs > workTs + FishContrast.HALF_HOUR) {
                        // 超过弹性时间（上午九点半）
                        mapper.insertTime(botReq.getUser_name(), workTs, inputTs);
                        botResp.setText("真鸡儿丢人！都超过弹性时间了，这大清药丸啊！（出勤时间已修改为：09:00）");
                    } else {
                        mapper.insertTime(botReq.getUser_name(), inputTs, inputTs);
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

}
