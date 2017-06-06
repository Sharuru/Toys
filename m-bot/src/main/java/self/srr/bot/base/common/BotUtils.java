package self.srr.bot.base.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import self.srr.bot.base.config.BotConfiguration;

/**
 * Bot tool utils
 * <p>
 * Created by Sharuru on 2017/06/06.
 */
@Component
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
     * @return response model
     */
    public static BotResponseModel getDefaultResponseModel() {
        BotResponseModel responseModel = new BotResponseModel();

        responseModel.setResponse_type(BotContrast.BOT_RESP_TYPE_EPH);
        responseModel.setUsername(_botConfiguration.getName());
        responseModel.setIcon_url(_botConfiguration.getIcon());
        responseModel.setText(BotContrast.BOT_DEFAULT_RESP);

        return responseModel;
    }
}
