package me.sharuru.mattermost.sum.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;@Service
@Slf4j
public class MattermostApiService {

    @Value("${msum.api-url}")
    private String apiUrl;

    public boolean grantAccess(String token) {
        if (StringUtils.isEmpty(token)) {
            return false;
        }
        // API
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl + "/users/me"))
                .header(
                        "Authorization", "Bearer " + token
                ).GET().build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            log.info(String.valueOf(response.statusCode()));
            log.info(response.body());
            //System.out.println(response.body());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return false;
    }
}


