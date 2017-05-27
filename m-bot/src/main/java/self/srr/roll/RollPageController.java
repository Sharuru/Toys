package self.srr.roll;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.NumberFormat;
import java.util.Map;
import java.util.Random;

/**
 * Roll 点页面路由
 * <p>
 * Created by Sharuru on 2017/5/27 0027.
 */
@Controller
@RequestMapping("/roll")
@Slf4j
public class RollPageController {

    @Autowired
    RollMapper rollMapper;

    @RequestMapping(value = "/switch", method = RequestMethod.GET)
    public String index(Model model, @RequestParam(name = "pass") String uid) {
        RollRecord rollRecord = rollMapper.findOneByUid(uid);
        if (rollRecord == null) {
            return "ERROR";
        } else {
            model.addAttribute("user", rollRecord);
            // 免费余额检查
            Long nowTs = System.currentTimeMillis() / 1000;
            if (nowTs - RollContrast.FREE_BALANCE.getLastCall() > 3600) {
                // 每小时自动刷新
                RollContrast.FREE_BALANCE.setAmount(10000.0 + new Random().nextInt(300));
                RollContrast.FREE_BALANCE.setLastCall(nowTs);
            }
            model.addAttribute("freeBalance", RollContrast.FREE_BALANCE.getAmount() >= 0 ? RollContrast.FREE_BALANCE.getAmount() : 0);
        }
        return "roll/switch";
    }

    @RequestMapping(value = "/switch", method = RequestMethod.POST)
    @ResponseBody
    public RollResponse charge(@RequestParam(name = "uid") String uid) {
        RollResponse resp = new RollResponse();
        RollRecord rollRecord = rollMapper.findOneByUid(uid);
        Double freeAmount = 5.0 + new Random().nextInt(10000) / 100.0;
        RollContrast.FREE_BALANCE.setAmount(RollContrast.FREE_BALANCE.getAmount() - freeAmount);
        rollMapper.updateAmount(uid, rollRecord.getAmount() + freeAmount, rollRecord.getStone());
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setGroupingUsed(false);
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        resp.setUid(rollRecord.getUid());
        resp.setAmount(nf.format(rollRecord.getAmount() + freeAmount));
        resp.setStone(String.valueOf(rollRecord.getStone()));
        resp.setChargeAmount(nf.format(freeAmount));
        resp.setFreeAmount(nf.format((RollContrast.FREE_BALANCE.getAmount())));

        return resp;
    }
}