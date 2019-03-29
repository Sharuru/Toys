package me.sharuru.mattermost.sum.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class MattermostApiService {

    public boolean grantAccess(String token) {
        if (!StringUtils.isEmpty(token)) {
            return false;
        }
        return false;
    }
}
