package me.sharuru.mattermost.sum.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import me.sharuru.mattermost.sum.common.utils.MsumUtils;
import me.sharuru.mattermost.sum.model.BusinessResponse;
import me.sharuru.mattermost.sum.model.CreateUserForm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.http.HttpResponse;

@Service
@Slf4j
public class MattermostService {

    @Value("${msum.api-url}")
    private String apiUrl;

    BusinessResponse isUserAdmin(String token) {
        HttpResponse<String> response = MsumUtils.httpGetResponse(apiUrl.concat("/users/me"), token);
        log.debug("isUserAdmin: {}", response.body());
        if (response.statusCode() == 200) {
            try {
                JsonNode responseNode = new ObjectMapper().readTree(response.body());
                String[] userRoles = responseNode.get("roles").asText().split(" ");
                for (String aRole : userRoles) {
                    if ("system_admin".equals(aRole.trim())) {
                        return new BusinessResponse(true);
                    }
                }
            } catch (IOException e) {
                log.error("Error on JSON resolve: {}, {}.", response.body(), e.getMessage());
            }
        }
        return new BusinessResponse(false);
    }

    BusinessResponse switchUserCreationFlag(boolean flag, String token) {
        HttpResponse<String> currentSystemConfiguration = MsumUtils.httpGetResponse(apiUrl.concat("/config"), token);
        log.debug("switchUserCreationFlag #1: {}", currentSystemConfiguration.body());
        if (currentSystemConfiguration.statusCode() == 200) {
            try {
                JsonNode currentNode = new ObjectMapper().readTree(currentSystemConfiguration.body());
                // TODO 设置开关
                ((ObjectNode) currentNode.get("TeamSettings")).put("EnableUserCreation", flag);
                // 回写
                HttpResponse<String> updatedSystemConfiguration = MsumUtils.httpPutResponse(apiUrl.concat("/config"), currentNode.toString(), token);
                log.debug("switchUserCreationFlag #2: {}", updatedSystemConfiguration.body());
                if (updatedSystemConfiguration.statusCode() == 200) {
                    try {
                        JsonNode updatedNode = new ObjectMapper().readTree(updatedSystemConfiguration.body());
                        return new BusinessResponse(updatedNode.get("TeamSettings").get("EnableUserCreation").asBoolean());
                    } catch (IOException e) {
                        log.error("Error on JSON resolve: {}, {}.", updatedSystemConfiguration.body(), e.getMessage());
                    }
                }
            } catch (IOException e) {
                log.error("Error on JSON resolve: {}, {}.", currentSystemConfiguration.body(), e.getMessage());
            }
        }
        return new BusinessResponse(false);
    }

    BusinessResponse createNewUser(CreateUserForm form, String token) {
        // TODO 创建请求 JSON
        ObjectNode userNode = new ObjectMapper().createObjectNode();
        userNode.put("username", form.getUsername().trim());
        userNode.put("email", form.getEmail().trim());
        userNode.put("first_name", form.getFirstname().trim());
        userNode.put("last_name", form.getLastname().trim());
        userNode.put("nickname", form.getNickname().trim());
        userNode.put("password", form.getPassword().trim());

        HttpResponse<String> response;
        response = MsumUtils.httpPostResponse(apiUrl.concat("/users"), userNode.toString(), token);
        log.debug("createNewUser: {}", response.body());
        if (response.statusCode() == 201) {
            try {
                // TODO 回写创建结果
                BusinessResponse retValue = new BusinessResponse(true);
                JsonNode createdUserNode = new ObjectMapper().readTree(response.body());
                retValue.setUsername(createdUserNode.get("username").textValue());
                retValue.setEmail(createdUserNode.get("email").textValue());
                retValue.setFirstname(createdUserNode.get("first_name").textValue());
                retValue.setLastname(createdUserNode.get("last_name").textValue());
                return retValue;
            } catch (IOException e) {
                log.error("Error on JSON resolve: {}, {}.", response.body(), e.getMessage());
            }
        }
        return new BusinessResponse(false, 103);
    }
}
