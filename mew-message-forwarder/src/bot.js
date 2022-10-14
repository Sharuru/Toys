import { logger, LogLevel, MewClient } from "mewbot";
import { botSettings } from "./settings.js";
import pageres from "pageres";
import uuid from "node-uuid";
import fs from "fs";

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
                // 跟踪截图文件名
                let screenshotFilename = "";
                if (typeof messageContext !== "undefined") {
                    messageContext += messageParameter;
                    // 强迫症，给备注加个句号。
                    messageContext = messageContext.slice(-1) === "。" ? messageContext : messageContext += "。"
                    // TODO 这写的啥玩意儿啊……
                    let messageReceiverId;
                    let messageThoughtId;
                    // 如果传进来的是想法 LINK 就从中间把用户 ID 扒拉出来，大小写无所谓
                    if (rawMessageRecevier.toLowerCase().includes("://mew.fun/")) {
                        let cleanedMessageRecevier = rawMessageRecevier.toLowerCase();
                        messageReceiverId = cleanedMessageRecevier.substring(cleanedMessageRecevier.indexOf("://mew.fun/") + 11, cleanedMessageRecevier.indexOf("/thoughts/"));
                        messageThoughtId = cleanedMessageRecevier.substring(cleanedMessageRecevier.indexOf("/thoughts/") + 10).trim();
                        // 顺便试试能不能截图
                        if (messageTemplateId === "删除" || messageTemplateId === "删除想法" || messageTemplateId === "警告" || messageTemplateId === "禁言") {
                            // 先取个文件名，方便之后删除
                            screenshotFilename = uuid.v4();
                            await new pageres({ delay: 2, filename: screenshotFilename, launchOptions: { args: ['--no-sandbox', '--disable-setuid-sandbox'] } })
                                .src(rawMessageRecevier, ['1920x1080'])
                                .dest(".")
                                .run();
                        }
                    } else {
                        // 假定传进来的就是用户 ID
                        messageReceiverId = rawMessageRecevier;
                    }
                    if (typeof messageReceiverId !== "undefined") {
                        // 取得用户信息（日志打着玩）
                        let receiverInfo = await client.getUserInfo(messageReceiverId);
                        receiverInfo = receiverInfo.data
                        // 取得私信信息（要 ID 发消息）
                        let receiverDirect = await client.getDirect(messageReceiverId);
                        receiverDirect = receiverDirect.data

                        console.log("准备发送消息给：%s（%s），内容为：%s", receiverInfo.name, receiverInfo.username, messageContext);
                        // 发消息给指定用户
                        await client.sendTextMessage(receiverDirect.id, messageContext);
                        // 如果截图文件名不为空，说明有截图，一起发了
                        if (screenshotFilename !== "") {
                            await client.sendTextMessage(receiverDirect.id, "相关内容截图：");
                            await client.sendImageMessage(receiverDirect.id, screenshotFilename + ".png");
                        }
                        // 响应结果给管理员
                        await client.sendTextMessage(data.topic_id, "消息已发送至：" + receiverInfo.name + "（" + receiverInfo.username + "），内容为：" + messageContext);
                        if (screenshotFilename !== "") {
                            await client.sendTextMessage(data.topic_id, "发送消息包含想法截图：");
                            await client.sendImageMessage(data.topic_id, screenshotFilename + ".png");
                            fs.unlinkSync(screenshotFilename + ".png");
                            screenshotFilename = "";
                        }
                    } else {
                        await client.sendTextMessage(data.topic_id, "收件人取得失败，消息未发送。");
                        console.log("收件人获取失败。");
                    }
                    // 偷懒，顺便自动删了
                    if(messageTemplateId === "删除想法"){
                        await client.deleteThought(messageThoughtId);
                        await client.sendTextMessage(data.topic_id, "想法已删除。");
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

logger.logLevel = LogLevel.Verbose;
