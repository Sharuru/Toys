package me.sharuru.srrbot.business.authorization;

import lombok.extern.slf4j.Slf4j;
import me.sharuru.srrbot.business.authorization.model.AuthRequestModel;
import me.sharuru.srrbot.business.authorization.model.AuthResponseModel;
import me.sharuru.srrbot.business.authorization.model.VerifyRequestModel;
import me.sharuru.srrbot.business.authorization.model.VerifyResponseModel;
import me.sharuru.srrbot.common.SrrBotUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class AuthorizationService {

    @Autowired
    SrrBotUtils srrBotUtils;

    public void refreshSessionKey() {
        RestTemplate restTemplate = new RestTemplate();
        AuthRequestModel authRequestModel = new AuthRequestModel();
        authRequestModel.setAuthKey(srrBotUtils.getAuthKey());
        HttpEntity<AuthRequestModel> authRequest = new HttpEntity<>(authRequestModel);
        AuthResponseModel authResponseModel = restTemplate.postForObject(srrBotUtils.getApiEndpoint() + "/auth", authRequest, AuthResponseModel.class);
        if (authResponseModel != null && authResponseModel.getCode() == 0 && !authResponseModel.getSession().isEmpty()) {
            VerifyRequestModel verifyRequestModel = new VerifyRequestModel();
            verifyRequestModel.setSessionKey(authResponseModel.getSession());
            verifyRequestModel.setQq(srrBotUtils.getBotQq());
            HttpEntity<VerifyRequestModel> verifyRequest = new HttpEntity<>(verifyRequestModel);
            VerifyResponseModel verifyResponseModel = restTemplate.postForObject(srrBotUtils.getApiEndpoint() + "/verify", verifyRequest, VerifyResponseModel.class);
            if (verifyResponseModel != null && verifyResponseModel.getCode() == 0 && "success".equals(verifyResponseModel.getMsg())) {
                srrBotUtils.setSessionKey(authResponseModel.getSession());
                log.info("Session key refreshed, {}", srrBotUtils.getSessionKey());
            }
        }
    }
}
