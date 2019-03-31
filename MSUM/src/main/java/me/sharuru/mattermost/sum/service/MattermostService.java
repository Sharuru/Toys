package me.sharuru.mattermost.sum.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import me.sharuru.mattermost.sum.common.utils.MsumUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.http.HttpResponse;

@Service
@Slf4j
public class MattermostService {

    @Value("${msum.api-url}")
    private String apiUrl;

    public boolean isUserAdmin(String token) {
        HttpResponse<String> response = MsumUtils.httpGetResponse(apiUrl.concat("/users/me"), token);
        if (response != null && response.statusCode() == 200) {
            try {
                JsonNode responseNode = new ObjectMapper().readTree(response.body());
                String[] userRoles = responseNode.get("roles").asText().split(" ");
                for (String aRole : userRoles) {
                    if ("system_admin".equals(aRole.trim())) {
                        return true;
                    }
                }
            } catch (IOException e) {
                log.error("Error on JSON resolve: {}, {}.", response.body(), e.getMessage());
            }
        }
        return false;
    }
}
