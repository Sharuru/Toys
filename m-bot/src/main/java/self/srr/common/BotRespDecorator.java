package self.srr.common;

import org.springframework.stereotype.Component;

/**
 * 响应实体通用装饰
 * <p>
 * Created by Sharuru on 2017/05/03.
 */
@Component
public class BotRespDecorator {

    /**
     * 是否频道内公开
     *
     * @param resp 原响应实体
     * @return 修改后实体
     */
    public BotResponseModel publicDecorator(BotResponseModel resp) {
        resp.setResponse_type(BotContrast.BOT_RES_TYPE_ICH);
        return resp;
    }

    /**
     * 是否增加提及信息
     *
     * @param resp 原响应实体
     * @param who  提及对象用户名
     * @return 修改后实体
     */
    public BotResponseModel atDecorator(BotResponseModel resp, String who) {
        resp.setText(resp.getText() + "  @" + who);
        return resp;
    }
}
