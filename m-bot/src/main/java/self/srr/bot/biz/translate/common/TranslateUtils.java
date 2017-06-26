package self.srr.bot.biz.translate.common;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import self.srr.bot.biz.translate.config.TranslateConfiguration;
import self.srr.bot.biz.translate.model.TranslateResponseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Translate utils
 * <p>
 * Created by Sharuru on 2017/06/23.
 */
@Component
@Slf4j
public class TranslateUtils {

    private static TranslateConfiguration _translateConfiguration;

    @Autowired
    public TranslateUtils(TranslateConfiguration translateConfiguration) {
        TranslateUtils._translateConfiguration = translateConfiguration;
    }

    // TODO salt is fixed
    private static final String SALT = "0000";

    /**
     * Request API and return response
     *
     * @param text source text
     * @param from from language code
     * @param to   to language code
     * @return response
     */
    public static TranslateResponseModel requestApi(String text, String from, String to) {
        TranslateResponseModel responseModel = new TranslateResponseModel();

        HttpPost httpPost = new HttpPost(_translateConfiguration.getUrl());

        List<NameValuePair> params = new ArrayList<>();

        params.add(new BasicNameValuePair("q", text));
        params.add(new BasicNameValuePair("from", from));
        params.add(new BasicNameValuePair("to", to));
        params.add(new BasicNameValuePair("appid", _translateConfiguration.getAppid()));
        params.add(new BasicNameValuePair("salt", SALT));
        params.add(new BasicNameValuePair("sign", getSign(text)));
        params.add(new BasicNameValuePair("charset", "UTF-8"));

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            HttpResponse response = HttpClients.createDefault().execute(httpPost);
            String responseStr = EntityUtils.toString(response.getEntity());
            log.info("API triggered with code " + response.getStatusLine().getStatusCode() + ": " + responseStr);
            responseModel = new Gson().fromJson(responseStr, TranslateResponseModel.class);
        } catch (Exception e) {
            log.error("Error happened in 'requestApi': " + e.getMessage());
        }

        return responseModel;
    }

    /**
     * Get sign for API request
     *
     * @param text raw text
     * @return md5 sign
     */
    private static String getSign(String text) {

        try {
            String signRaw = _translateConfiguration.getAppid() + text + SALT + _translateConfiguration.getSecret();
            return DigestUtils.md5DigestAsHex(signRaw.getBytes("UTF-8"));
        } catch (Exception e) {
            log.error("Error happened in `getSign`: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
