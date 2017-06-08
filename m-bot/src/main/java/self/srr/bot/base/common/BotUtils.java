package self.srr.bot.base.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import self.srr.bot.base.config.BotConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Bot tool utils
 * <p>
 * Created by Sharuru on 2017/06/06.
 */
@Component
@Slf4j
public class BotUtils {

    private static BotConfiguration _botConfiguration;

    @Autowired
    public BotUtils(BotConfiguration botConfiguration) {
        BotUtils._botConfiguration = botConfiguration;
    }

    /**
     * Check if the user is the master in settings
     *
     * @param userId user id
     * @return master flag
     */
    public static Boolean isUserMaster(String userId) {
        if (_botConfiguration.getMasters().size() != 0) {
            for (String master : _botConfiguration.getMasters()) {
                if (master.equals(userId)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Get default response model
     *
     * @param args user command
     * @return response
     */
    public static BotResponseModel getDefaultResponseModel(String... args) {
        BotResponseModel responseModel = new BotResponseModel();

        responseModel.setResponse_type(BotContrast.BOT_RESP_TYPE_EPH);
        responseModel.setUsername(_botConfiguration.getName());
        responseModel.setIcon_url(_botConfiguration.getIcon());
        responseModel.setText(BotContrast.BOT_DEFAULT_RESP);

        if (args.length != 0 && "s".equalsIgnoreCase(args[args.length - 1])) {
            responseModel.setIsPublic(true);
        }

        return responseModel;
    }

    /**
     * Send webhook request
     *
     * @param url  target url
     * @param text payload
     * @return status code
     */
    public static int triggerHook(String url, String text) {
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> params = new ArrayList<>();

        params.add(new BasicNameValuePair("payload",
                "{" +
                        "\"username\": \"" + _botConfiguration.getName() + "\"," +
                        "\"icon_url\": \"" + _botConfiguration.getIcon() + "\"," +
                        "\"text\": \" " + text + "\"" +
                        "}"));
        params.add(new BasicNameValuePair("charset", "UTF-8"));

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            HttpResponse response = HttpClients.createDefault().execute(httpPost);
            log.info("Webhook triggered with code " + response.getStatusLine().getStatusCode() + ": " + response.getStatusLine().getReasonPhrase());
        } catch (Exception e) {
            log.error("Error happened in 'triggerHook': " + e.getMessage());
            return -1;
        }
        return 0;
    }

    /**
     * User command parser
     *
     * @param input command
     * @return parsed string array
     */
    public static String[] commandParser(String input) {
        return input.split("\\s+");
    }


}
