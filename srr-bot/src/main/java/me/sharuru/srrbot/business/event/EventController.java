package me.sharuru.srrbot.business.event;

import lombok.extern.slf4j.Slf4j;
import me.sharuru.srrbot.business.authorization.AuthorizationService;
import me.sharuru.srrbot.business.event.model.EventModel;
import me.sharuru.srrbot.business.event.model.GroupMessageRequestModel;
import me.sharuru.srrbot.business.event.model.GroupMessageResponseModel;
import me.sharuru.srrbot.business.event.model.MessageChainModel;
import me.sharuru.srrbot.common.SrrBotUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

// TODO all
@Slf4j
@Controller
@RequestMapping("/event")
public class EventController {

    @Autowired
    AuthorizationService authorizationService;

    @Autowired
    SrrBotUtils srrBotUtils;

    @Value("${srrBot.targetQqGroup}")
    private Long TARGET_QQ_GROUP;

    @ResponseBody
    @RequestMapping("/push")
    public String response(@RequestBody EventModel eventModel) {
        if (eventModel.getType().equals("GroupMessage")) {
            if (TARGET_QQ_GROUP.equals(eventModel.getSender().getGroup().getId()) && "Plain".equals(eventModel.getMessageChain().get(1).getType())) {
                String msgText = eventModel.getMessageChain().get(1).getText().replaceAll("\\s+", "");
                if (msgText.contains("ruru") || msgText.contains("如如")) {
                    log.info("Get keyword message: {}", eventModel);

                    RestTemplate restTemplate = new RestTemplate();
                    GroupMessageRequestModel groupMessageRequestModel = new GroupMessageRequestModel();
                    groupMessageRequestModel.setTarget(TARGET_QQ_GROUP);
                    groupMessageRequestModel.setSessionKey(srrBotUtils.getSessionKey());

                    MessageChainModel messagePayload = new MessageChainModel();
                    messagePayload.setType("Plain");

                    switch (msgText) {
                        case "投喂如如":
                        case "投喂ruru": {
                            List<String> responseMsgList = new ArrayList<>();
                            responseMsgList.add("感谢投喂，mua~");
                            responseMsgList.add("唔…我吃不下啦！");
                            responseMsgList.add("嗯？谢谢投喂。");
                            responseMsgList.add("如果哪天如如成为了 debu，其中 99.99% 都是你的错啦！ε=ε=ε=(~￣▽￣)~");
                            responseMsgList.add("哼，挖路酷奈衣。");
                            responseMsgList.add("唔姆唔姆，谢谢！");
                            responseMsgList.add("mua~");
                            responseMsgList.add("啊噗噜派~！");

                            messagePayload.setText(responseMsgList.get(new Random().nextInt(responseMsgList.size())));
                            groupMessageRequestModel.getMessageChain().add(messagePayload);
                            break;
                        }
                        case "抢如如投喂":
                        case "抢ruru投喂": {
                            List<String> responseMsgList = new ArrayList<>();
                            responseMsgList.add("呜呜呜，为什么要做这样的事情……");
                            responseMsgList.add("QAQ");
                            responseMsgList.add("这种水平的抢夺，还请允许我保留体力。");
                            responseMsgList.add("没事，被抢掉的投喂从阿比那里再抢过来就好啦！（屑）");
                            responseMsgList.add("如如开始变得有点讨厌尼惹 QwQ");
                            responseMsgList.add("我数到三，你们还有投降的机会，三~");
                            responseMsgList.add("没事，反正如如也不喜欢这个……大概……（泣）");
                            responseMsgList.add("坏东西，坏东西！");
                            responseMsgList.add("嗯......我，我会忍住不搞折你的手......");
                            responseMsgList.add("呵......");
                            responseMsgList.add(".rd 这合适吗？不合适吧！");

                            messagePayload.setText(responseMsgList.get(new Random().nextInt(responseMsgList.size())));
                            groupMessageRequestModel.getMessageChain().add(messagePayload);
                            break;
                        }
                        case "恰ruru":
                        case "恰如如": {
                            List<String> responseMsgList = new ArrayList<>();
                            responseMsgList.add("不准恰，不准恰，我不好恰！");
                            responseMsgList.add("去恰阿比，阿比好恰！");
                            responseMsgList.add("我还没熟……（安详）");
                            responseMsgList.add("如果……只是恰一口的话……（伸出手臂）");
                            responseMsgList.add("说好了，只能恰一口哦！（撅屁股——）");
                            responseMsgList.add("好怪哦……");
                            responseMsgList.add("有点奇怪，不过，也不算讨厌……");
                            responseMsgList.add("嘣恰恰嘣恰恰嘣恰恰~是要和如如一起跳舞吗？");
                            responseMsgList.add("想恰如如的话，应该也做好了被如如恰的觉悟了吧？（咬）");

                            messagePayload.setText(responseMsgList.get(new Random().nextInt(responseMsgList.size())));
                            groupMessageRequestModel.getMessageChain().add(messagePayload);
                            break;
                        }
                        case "ruaruru":
                        case "rua如如": {
                            List<String> responseMsgList = new ArrayList<>();
                            responseMsgList.add("rua！");
                            responseMsgList.add("不准 rua！人家刚顺好的毛 QwQ");
                            responseMsgList.add("很舒服呢，谢谢你！");
                            responseMsgList.add("忒嘿~ (●'◡'●)");
                            responseMsgList.add("我喜欢这种感觉！");
                            responseMsgList.add("至……福…… >////<");
                            responseMsgList.add("mofumofu，谢谢你！");
                            responseMsgList.add("不，不要碰我！......你会受伤的。");
                            responseMsgList.add("唔啊......好困......干脆一起打个盹吧......");
                            responseMsgList.add("为了能让你 rua 的舒服一点，如如我天天都有在好好的做着护理哦~");
                            responseMsgList.add("（丢人的声音）");

                            messagePayload.setText(responseMsgList.get(new Random().nextInt(responseMsgList.size())));
                            groupMessageRequestModel.getMessageChain().add(messagePayload);
                            break;
                        }
                        default:
                            return "NOT_CARE";
                    }

                    HttpEntity<GroupMessageRequestModel> groupMessageRequest = new HttpEntity<>(groupMessageRequestModel);
                    GroupMessageResponseModel groupMessageResponseModel = restTemplate.postForObject(srrBotUtils.getApiEndpoint() + "/sendGroupMessage", groupMessageRequest, GroupMessageResponseModel.class);
                    if (groupMessageResponseModel != null && groupMessageResponseModel.getCode() != 0) {
                        log.warn("Session may expired, refreshing...");
                        authorizationService.refreshSessionKey();
                        groupMessageRequestModel.setSessionKey(srrBotUtils.getSessionKey());
                        groupMessageResponseModel = restTemplate.postForObject(srrBotUtils.getApiEndpoint() + "/sendGroupMessage", groupMessageRequest, GroupMessageResponseModel.class);
                    }
                    log.info("Message sent: {}", groupMessageResponseModel == null ? "" : groupMessageResponseModel.toString());
                } else if ("晚安".equals(msgText) || "晚安。".equals(msgText)) {
                    RestTemplate restTemplate = new RestTemplate();
                    GroupMessageRequestModel groupMessageRequestModel = new GroupMessageRequestModel();
                    groupMessageRequestModel.setTarget(TARGET_QQ_GROUP);
                    groupMessageRequestModel.setSessionKey(srrBotUtils.getSessionKey());

                    MessageChainModel messagePayload = new MessageChainModel();
                    messagePayload.setType("Plain");

                    messagePayload.setText("仰望星辉\n如银之诗篇一般的感情\n沉默将声音隐藏住 在无色之中溶解了\n最后你也化为虚幻\n请于星辉海水下长眠.....至少现在");
                    groupMessageRequestModel.getMessageChain().add(messagePayload);

                    if (ThreadLocalRandom.current().nextInt(1, 11) <= 3) {
                        MessageChainModel messagePicPayload = new MessageChainModel();
                        messagePicPayload.setText("Image");
                        messagePicPayload.setUrl("https://i.loli.net/2021/08/01/yYjqiP2pCblKrfc.png");
                        groupMessageRequestModel.getMessageChain().add(messagePayload);
                    } else if (ThreadLocalRandom.current().nextInt(1, 11) >= 7) {
                        MessageChainModel messagePicPayload = new MessageChainModel();
                        messagePicPayload.setText("Image");
                        messagePicPayload.setUrl("https://i.loli.net/2021/08/01/TUSd289tXP6bHpq.png");
                        groupMessageRequestModel.getMessageChain().add(messagePayload);
                    }

                    HttpEntity<GroupMessageRequestModel> groupMessageRequest = new HttpEntity<>(groupMessageRequestModel);
                    GroupMessageResponseModel groupMessageResponseModel = restTemplate.postForObject(srrBotUtils.getApiEndpoint() + "/sendGroupMessage", groupMessageRequest, GroupMessageResponseModel.class);
                    if (groupMessageResponseModel != null && groupMessageResponseModel.getCode() != 0) {
                        log.warn("Session may expired, refreshing...");
                        authorizationService.refreshSessionKey();
                        groupMessageRequestModel.setSessionKey(srrBotUtils.getSessionKey());
                        groupMessageResponseModel = restTemplate.postForObject(srrBotUtils.getApiEndpoint() + "/sendGroupMessage", groupMessageRequest, GroupMessageResponseModel.class);
                    }
                    log.info("Message sent: {}", groupMessageResponseModel == null ? "" : groupMessageResponseModel.toString());
                } else if ("投喂F5".equalsIgnoreCase(msgText)) {
                    RestTemplate restTemplate = new RestTemplate();
                    GroupMessageRequestModel groupMessageRequestModel = new GroupMessageRequestModel();
                    groupMessageRequestModel.setTarget(TARGET_QQ_GROUP);
                    groupMessageRequestModel.setSessionKey(srrBotUtils.getSessionKey());

                    MessageChainModel messagePayload = new MessageChainModel();
                    messagePayload.setType("Plain");

                    List<String> responseMsgList = new ArrayList<>();
                    responseMsgList.add("要投喂的是 F5……而不是可爱的如如吗……？");
                    responseMsgList.add("谢谢！F5 让我谢谢你！");
                    responseMsgList.add("摸摸，摸摸，只要你投喂 F5，我们就是好朋友啦！");
                    responseMsgList.add("有时候，会觉得……F5 真的是被大家爱着呢……");
                    responseMsgList.add("谢谢投喂！……欸……原来不是投喂如如啊……（探头）");

                    messagePayload.setText(responseMsgList.get(new Random().nextInt(responseMsgList.size())));
                    groupMessageRequestModel.getMessageChain().add(messagePayload);

                    HttpEntity<GroupMessageRequestModel> groupMessageRequest = new HttpEntity<>(groupMessageRequestModel);
                    GroupMessageResponseModel groupMessageResponseModel = restTemplate.postForObject(srrBotUtils.getApiEndpoint() + "/sendGroupMessage", groupMessageRequest, GroupMessageResponseModel.class);
                    if (groupMessageResponseModel != null && groupMessageResponseModel.getCode() != 0) {
                        log.warn("Session may expired, refreshing...");
                        authorizationService.refreshSessionKey();
                        groupMessageRequestModel.setSessionKey(srrBotUtils.getSessionKey());
                        groupMessageResponseModel = restTemplate.postForObject(srrBotUtils.getApiEndpoint() + "/sendGroupMessage", groupMessageRequest, GroupMessageResponseModel.class);
                    }
                    log.info("Message sent: {}", groupMessageResponseModel == null ? "" : groupMessageResponseModel.toString());
                } else if ("抢F5投喂".equalsIgnoreCase(msgText)) {
                    RestTemplate restTemplate = new RestTemplate();
                    GroupMessageRequestModel groupMessageRequestModel = new GroupMessageRequestModel();
                    groupMessageRequestModel.setTarget(TARGET_QQ_GROUP);
                    groupMessageRequestModel.setSessionKey(srrBotUtils.getSessionKey());

                    MessageChainModel messagePayload = new MessageChainModel();
                    messagePayload.setType("Plain");

                    List<String> responseMsgList = new ArrayList<>();
                    responseMsgList.add("稍微欺负一下 F5，感觉也不错呢！");
                    responseMsgList.add("不准抢如如的投喂！……嗯？你说，你抢的是 F5 的？（你请）");
                    responseMsgList.add("高价回收 F5 投喂，价格优惠，可换不锈钢脸盆~");
                    responseMsgList.add("真是可怕的行为……希望不要来抢如如的投喂……");
                    responseMsgList.add("哟西哟西~这样，F5 就更加离不开如如了呢。嘿嘿……嘿嘿……");

                    messagePayload.setText(responseMsgList.get(new Random().nextInt(responseMsgList.size())));
                    groupMessageRequestModel.getMessageChain().add(messagePayload);

                    HttpEntity<GroupMessageRequestModel> groupMessageRequest = new HttpEntity<>(groupMessageRequestModel);
                    GroupMessageResponseModel groupMessageResponseModel = restTemplate.postForObject(srrBotUtils.getApiEndpoint() + "/sendGroupMessage", groupMessageRequest, GroupMessageResponseModel.class);
                    if (groupMessageResponseModel != null && groupMessageResponseModel.getCode() != 0) {
                        log.warn("Session may expired, refreshing...");
                        authorizationService.refreshSessionKey();
                        groupMessageRequestModel.setSessionKey(srrBotUtils.getSessionKey());
                        groupMessageResponseModel = restTemplate.postForObject(srrBotUtils.getApiEndpoint() + "/sendGroupMessage", groupMessageRequest, GroupMessageResponseModel.class);
                    }
                    log.info("Message sent: {}", groupMessageResponseModel == null ? "" : groupMessageResponseModel.toString());
                }
            }
        }
        return "OK";
    }

}
