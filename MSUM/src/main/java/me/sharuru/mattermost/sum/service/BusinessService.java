package me.sharuru.mattermost.sum.service;

import lombok.extern.slf4j.Slf4j;
import me.sharuru.mattermost.sum.model.CreateUserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BusinessService {

    @Autowired
    MattermostService mattermostService;

    public int createUser(CreateUserForm form){
        // 检查用户权限
        if(mattermostService.isUserAdmin(form.getToken())){
            // 获取并打开创建开关
            // 创建用户
            // 获取并关闭创建开关
            return 0;
        }
        // 返回
        return 100;
    }
}


