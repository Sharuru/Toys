package me.sharuru.mattermost.sum.service;

import lombok.extern.slf4j.Slf4j;
import me.sharuru.mattermost.sum.model.BusinessResponse;
import me.sharuru.mattermost.sum.model.CreateUserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BusinessService {

    @Autowired
    MattermostService mattermostService;

    public BusinessResponse createUser(CreateUserForm form) {
        // 检查用户权限
        if (!mattermostService.isUserAdmin(form.getToken()).isStatus()) {
            log.info("Failed on user permission check.");
            return new BusinessResponse(false, 101);
        }
        // 获取并打开创建开关
        if (!mattermostService.switchUserCreationFlag(true, form.getToken()).isStatus()) {
            log.info("Failed on system configuration update.");
            return new BusinessResponse(false, 102);
        }
        // 创建用户
        BusinessResponse createUserResponse = mattermostService.createNewUser(form, form.getToken());
        if (createUserResponse.isStatus()) {
            createUserResponse.setCode(0);
            // TODO 关闭时可能错误
            // 获取并关闭创建开关
            mattermostService.switchUserCreationFlag(false, form.getToken());
            log.info("User creation succeed.");
            return createUserResponse;
        } else {
            log.info("Failed on user creation.");
            return createUserResponse;
        }
    }

}


