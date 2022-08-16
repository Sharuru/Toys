import { logger, LogLevel, MewClient } from "mewbot";
import { botSettings } from "./settings.js";

const client = new MewClient();

// TODO 整坨代码都在摆
client.on('message_create', async (data) => {
    // 私信且非来自黑名单的用户
    if (data._isDirect === true && !botSettings.blacklistIds.includes(data.author_id)) {
        console.dir(data);
        // 管理员用户
        if (botSettings.administratorIds.includes(data.author_id)) {
            console.log("接收到来自管理员：%s（%s）的消息：\n%s", data._author.name, data._author.username, data.content);
            let splitedCommandArgs = data.content.split("#");
            if (splitedCommandArgs.length !== 3) {
                await client.sendTextMessage(data.topic_id, "指令识别失败，消息未发送");
                console.log("指令识别失败");
            } else {
                // 简单切一下指令
                const messageTemplateId = splitedCommandArgs[0];
                const rawMessageRecevier = splitedCommandArgs[1].replaceAll("\n", "");
                const messageParameter = splitedCommandArgs[2];
                // 取得消息模板
                let messageContext = botSettings.messageTemplates[messageTemplateId];
                if (typeof messageContext !== "undefined") {
                    messageContext += messageParameter;
                    // 强迫症，给备注加个句号。
                    messageContext = messageContext.slice(-1) === "。" ? messageContext : messageContext += "。"
                    // TODO 这写的啥玩意儿啊……
                    let messageReceiver;
                    // 如果传进来的是想法 LINK 就从中间把用户 ID 扒拉出来，大小写无所谓
                    if (rawMessageRecevier.toLowerCase().includes("://mew.fun/")) {
                        let cleanedMessageRecevier = rawMessageRecevier.toLowerCase();
                        messageReceiver = cleanedMessageRecevier.substring(cleanedMessageRecevier.indexOf("://mew.fun/") + 11, cleanedMessageRecevier.indexOf("/thoughts/"));
                    } else {
                        // 假定传进来的就是用户 ID
                        messageReceiver = rawMessageRecevier;
                    }
                    if (typeof messageReceiver !== "undefined") {
                        // 取得用户信息（日志打着玩）
                        let receiverInfo = await client.getUserInfo(messageReceiver);
                        receiverInfo = receiverInfo.data
                        // 取得私信信息（要 ID 发消息）
                        let receiverDirect = await client.getDirect(messageReceiver);
                        receiverDirect = receiverDirect.data

                        console.log("准备发送消息给：%s（%s），内容为：%s", receiverInfo.name, receiverInfo.username, messageContext);
                        // 发消息给指定用户
                        await client.sendTextMessage(receiverDirect.id, messageContext);
                        // 响应结果给管理员
                        await client.sendTextMessage(data.topic_id, "消息已发送至：" + receiverInfo.name + "（" + receiverInfo.username + "），内容为：" + messageContext);
                    } else {
                        await client.sendTextMessage(data.topic_id, "收件人取得失败，消息未发送。");
                        console.log("收件人获取失败。");
                    }
                } else {
                    await client.sendTextMessage(data.topic_id, "消息模板不正确，消息未发送。");
                    console.log("不存在的消息模板：%s", messageTemplateId);
                }
            }
        } else {
            // 一般用户
            console.log("接收到来自一般用户：%s（%s）的消息：\n%s", data._author.name, data._author.username, data.content);
            await client.sendTextMessage(data.topic_id, botSettings.messageTemplates.autoReplyMessage);
            console.log("自动回复已发送");
        }
    }

});

client.setToken(botSettings.loginToken);

client.connect({ subcriptionNodes: botSettings.subcriptionNodes });

logger.logLevel = LogLevel.Info;
