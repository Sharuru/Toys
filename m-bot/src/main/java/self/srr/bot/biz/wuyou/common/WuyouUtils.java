package self.srr.bot.biz.wuyou.common;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;
import self.srr.bot.biz.wuyou.model.FundInfoResponseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Wuyou utils
 * <p>
 * Created by Sharuru on 2017/06/23.
 */
@Component
@Slf4j
public class WuyouUtils {

    /**
     * Request fund info and return response
     *
     * @param fundCode fund code
     * @return response
     */
    public static FundInfoResponseModel requestFundInfo(String fundCode) {
        FundInfoResponseModel responseModel = new FundInfoResponseModel();

        HttpPost httpPost = new HttpPost("https://fundmobapi.eastmoney.com/FundMNewApi/FundMNFInfo");

        List<NameValuePair> params = new ArrayList<>();

        params.add(new BasicNameValuePair("appType", "ttjj"));
        params.add(new BasicNameValuePair("product", "EFund"));
        params.add(new BasicNameValuePair("plat", "Android"));
        params.add(new BasicNameValuePair("deviceid", "9e16077fca2fcr78ep0ltn98"));
        params.add(new BasicNameValuePair("Version", "1"));
        params.add(new BasicNameValuePair("Fcodes", fundCode));

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            HttpResponse response = HttpClients.createDefault().execute(httpPost);
            String responseStr = EntityUtils.toString(response.getEntity());
            log.info("API triggered with code " + response.getStatusLine().getStatusCode() + ": " + responseStr);
            responseModel = new Gson().fromJson(responseStr, FundInfoResponseModel.class);
        } catch (Exception e) {
            log.error("Error happened in 'requestFundInfo': " + e.getMessage());
        }

        return responseModel;
    }

}
