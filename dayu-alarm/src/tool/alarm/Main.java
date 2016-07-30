package tool.alarm;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcTtsNumSinglecallRequest;
import com.taobao.api.response.AlibabaAliqinFcTtsNumSinglecallResponse;

import static tool.alarm.ConstantsConfiguration.*;


public class Main {

    public static void main(String[] args) {
        TaobaoClient client = new DefaultTaobaoClient(PUBLIC.URL, PUBLIC.APP_KEY, PUBLIC.SECRET);
        AlibabaAliqinFcTtsNumSinglecallRequest req = new AlibabaAliqinFcTtsNumSinglecallRequest();
        //req.setExtend("");
        req.setTtsParamString(TTS_PARAM.NAME);
        req.setCalledNum(TTS_API.CALLED_NUM);
        req.setCalledShowNum(TTS_API.CALLED_SHOW_NUM);
        req.setTtsCode(TTS_API.TTS_CODE);
        AlibabaAliqinFcTtsNumSinglecallResponse rsp = null;
        try {
            rsp = client.execute(req);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        System.out.println(rsp.getBody());
    }
}

/**
 * 应用运行相关的常量配置
 */
class ConstantsConfiguration {

    /**
     * 公共部
     */
    public static class PUBLIC {
        //请求地址
        public static final String URL = "";
        //应用 KEY
        public static final String APP_KEY = "";
        //应用密钥
        public static final String SECRET = "";
    }

    /**
     * TTS 接口配置部
     */
    public static class TTS_API {
        //被叫号码
        public static final String CALLED_NUM = "";
        //被叫号显
        public static final String CALLED_SHOW_NUM = "";
        //TTS 模板 ID
        public static final String TTS_CODE = "";
    }

    /**
     * TTS 接口参数部
     */
    public static class TTS_PARAM {
        //参数
        public static final String NAME = "";
    }

}
