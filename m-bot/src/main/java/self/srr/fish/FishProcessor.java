package self.srr.fish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import self.srr.common.BotRequestModel;
import self.srr.common.BotResponseModel;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 摸鱼计时的业务实现
 * <p>
 * Created by Sharuru on 2017/04/28.
 */
@Service
public class FishProcessor {

    @Autowired
    FishMapper mapper;

    BotResponseModel botResp = new BotResponseModel();
    BotRequestModel botReq = new BotRequestModel();

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
        }
        return botResp;
    }

    private void showHelpMsg() {
        String text = "" +
                "摸鱼计时的帮助信息：\n" +
                "使用方法：输入 `/fish 指令`\n" +
                "`/fish` 显示今天还得摸多久才能跑路" +
                "`/fish help` 显示本帮助信息\n" +
                "`/fish ci 0900 记录今天的签到时间为上午九点，再次使用则进行修改" +
                "* 所有时间均已 +8 区为基准进行计算 *";
        botResp.setText(text);
    }

    //

    private void regTime(String timeStr) {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Shanghai"));
        ZonedDateTime inputTime = null;
        // 检测是否是时间格式
        try {
            inputTime = ZonedDateTime.of(now.getYear(),now.getMonthValue(),now.getDayOfMonth(),Integer.valueOf(timeStr.substring(0,2)),Integer.valueOf(timeStr.substring(2,4)),0,0,ZoneId.of("Asia/Shanghai"));
            try {
                // 转换成对应的形式写入数据库
                // 试着查询是否已有记录
                ZonedDateTime todayBegin = ZonedDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 0, 0, 0, 0, ZoneId.of("Asia/Shanghai"));
                Long beginTs = Date.from(todayBegin.toInstant()).getTime() / 1000;
                Long endTs = beginTs + 86400;
                Long workTs = beginTs + 32400;
                Long inputTs = Date.from(inputTime.toInstant()).getTime() / 1000;
                FishRecord extRecord = mapper.findOneByTime(botReq.getUser_name(), beginTs, endTs);
                if(extRecord != null){
                    // 今日已登记过，修改记录
                    if(inputTs > workTs + 1800){
                        // 超过弹性时间
                        mapper.updateTimeById(extRecord.getId(),workTs,inputTs);
                        botResp.setText("真鸡儿丢人！都超过弹性时间了！");
                    }else{
                        mapper.updateTimeById(extRecord.getId(),inputTs,inputTs);
                        botResp.setText("今天的考勤时间已经修改成：" + inputTime.format(DateTimeFormatter.ofPattern("HH:mm")));
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            botResp.setText("时间格式输入错误了哟");
        }
    }

}
