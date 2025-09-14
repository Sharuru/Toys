package me.sharuru.matterslash.service;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import lombok.extern.slf4j.Slf4j;
import me.sharuru.matterslash.model.CommandRequestPayload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class TranslateService {

    @Value("${slash.translate.openAiKey}")
    private String openAiKey;

    @Value("${slash.translate.apiBaseUrl}")
    private String apiBaseUrl;

    /**
     * 翻译功能的帮助信息
     */
    private static final String HELP_MESSAGE = """
            翻译功能（OpenAI）的帮助信息：
            
            【使用方法】
            输入 `/fyp 你的输入内容`。
            
            【默认翻译规则】
            1. 自动检测输入语言
            2. 日语 → 中文 + 英文
            3. 英文 → 中文 + 日语（含平假名读音）
            4. 中文 → 日语（含平假名读音）+ 英文
            
            【指定目标语言】
            支持指定翻译目标，如：
            - 将XXX翻译成日语。
            - 翻译成日语：XXX。
            - 翻译成英文：XXX。
            
            【注意事项】
            - 目前翻译模型：GPT-4.1。
            - 生成式 AI 响应时间较长，单词翻译超时时间为 1 分钟，长文本建议分段发送。
            - 上次请求未完成前，系统不接受新请求。
            - 翻译内容仅供参考。
            """;

    /**
     * 获取帮助信息
     */
    public String getHelpMessage() {
        return HELP_MESSAGE;
    }

    /**
     * 异步翻译方法，使用 OpenAI 官方异步 API
     */
    public CompletableFuture<String> translateAsync(final String userInput) {
        String userInputTxt = userInput.trim();

        // 检查输入是否为空
        if (userInputTxt.isEmpty()) {
            return CompletableFuture.completedFuture("输入的命令无法识别，请确认。如需帮助，可使用 `help` 命令。");
        }

        // 处理 help 命令
        if ("help".equals(userInputTxt)) {
            return CompletableFuture.completedFuture(HELP_MESSAGE);
        }

        try {
            OpenAIClient client = OpenAIOkHttpClient.builder()
                    .apiKey(openAiKey)
                    .baseUrl(apiBaseUrl)
                    .timeout(Duration.ofSeconds(60))
                    .build();

            String instruction = """
                    Developer: 你是一名专业的翻译助手，仅根据默认规则或用户指令对指定内容进行翻译，仅输出翻译结果，不输出任何额外信息。
                    
                    # 关键规则
                    - 仅输出翻译内容，不输出额外信息。如用户指令不明确，拒绝翻译并提示用户补充需求。
                    
                    # 输出格式
                    - 【原文（原文语种）】：
                    - 【目标语种1】：
                    - 【目标语种2】：
                    
                    # 翻译规则
                    ## 默认规则（如用户未指定目标语言）：
                    1. 自动检测用户输入的语言，将其视为原文。
                    2. 输入为日语时，自动翻译为中文和英文。
                    3. 输入为英语时，自动翻译为中文和日语，同时提供日语平假名读音。
                    4. 输入为中文时，自动翻译为日语和英文，同时提供日语平假名读音。
                    - 翻译为日语时，确保表达准确，符合日语语法，保持适度礼貌但不过度，避免使用过于书面化的汉字。
                    
                    ## 指定目标语言（如：将XXX翻译成日语，或：翻译成日语：XXX）：
                    1. 按用户要求翻译为指定目标语言。
                    2. 如目标语言为日语，同时需提供平假名读音。
                    - 翻译为日语时，确保表达准确，符合日语语法，保持适度礼貌但不过度，避免使用过于书面化的汉字。
                    
                    # 操作与自查
                    - 每次翻译后，验证输出内容是否符合上述规则，如有不符，进行最小修正后输出。
                    """;

            ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                    .addSystemMessage(instruction)
                    .addUserMessage(userInputTxt)
                    .model(ChatModel.GPT_4_1)
                    .build();

            // 使用 OpenAI 官方异步 API
            CompletableFuture<ChatCompletion> chatCompletion = client.async().chat().completions().create(params);

            return chatCompletion.thenApply(completion -> {
                StringBuilder result = new StringBuilder();
                completion.choices().stream()
                        .flatMap(choice -> choice.message().content().stream())
                        .forEach(result::append);
                return result.toString();
            }).exceptionally(throwable -> {
                log.error("[Translate] Error calling OpenAI API", throwable);
                return "翻译服务暂时不可用，请稍后重试。";
            });

        } catch (Exception e) {
            log.error("[Translate] Error setting up OpenAI client", e);
            return CompletableFuture.completedFuture("翻译服务暂时不可用，请稍后重试。");
        }
    }

    /**
     * 通过 Mattermost webhook 发送响应
     */
    public void sendWebhookResponse(String responseUrl, String text) {
        if (responseUrl == null || responseUrl.trim().isEmpty()) {
            log.warn("[Translate] Response URL is null or empty, cannot send webhook response");
            return;
        }

        try {
            RestTemplate restTemplate = new RestTemplate();

            // 构建响应数据
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("response_type", "ephemeral");
            responseData.put("text", text);

            // 设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(responseData, headers);

            // 发送 POST 请求到 Mattermost response_url
            restTemplate.postForObject(responseUrl, request, String.class);

            log.info("[Translate] Webhook response sent successfully");
        } catch (Exception e) {
            log.error("[Translate] Error sending webhook response to URL: {}", responseUrl, e);
        }
    }

    /**
     * 异步处理翻译任务，使用 OpenAI 官方异步 API
     */
    public CompletableFuture<Void> processTranslationAsync(CommandRequestPayload payload) {
        return translateAsync(payload.getText().trim())
                .thenAccept(translationResult -> {
                    // 通过 response_url 发送翻译结果
                    sendWebhookResponse(payload.getResponse_url(), translationResult);
                    log.info("[Translate] async translation completed and sent via webhook");
                })
                .exceptionally(throwable -> {
                    log.error("[Translate] Error in async translation", throwable);
                    // 发送错误消息
                    sendWebhookResponse(payload.getResponse_url(), "翻译服务暂时不可用，请稍后重试。");
                    return null;
                });
    }
}
