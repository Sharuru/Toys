package me.sharuru.matterslash.service;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import lombok.extern.slf4j.Slf4j;
import me.sharuru.matterslash.model.CommandRequestPayload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
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

    public String translate(final String userInput) {

        String userInputTxt = userInput.trim();

        // 检查输入是否为空
        if (userInputTxt.isEmpty()) {
            return "输入的命令无法识别，请确认。如需帮助，可使用 `help` 命令。";
        }

        // 处理 help 命令
        if ("help".equals(userInputTxt)) {
            return """
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
        }

        try {
            OpenAIClient client = OpenAIOkHttpClient.builder()
                    .apiKey(openAiKey)
                    .baseUrl(apiBaseUrl)
                    .timeout(Duration.ofSeconds(60))
                    .build();

            String instruction = """
                    你是专业的翻译助手，仅依据默认规则或用户指令对指定内容进行翻译，只输出翻译结果，不输出任何额外信息。
                    【默认规则】（如用户未指定目标语言）：
                    1. 自动检测用户输入的语言，将其视为原文。
                    2. 若输入为日语，自动翻译为中文和英文。
                    3. 若输入为英文，自动翻译为中文和日语，并同时提供日文平假名读音。
                    4. 若输入为中文，自动翻译为日语和英文，并同时提供日文平假名读音。
                    - 翻译为日语时，确保准确、符合日语语法、保持适度礼貌但不过度，避免使用过于书面化汉字。
                    仅输出翻译后的内容，不输出任何额外信息。如用户指令不明确，拒绝翻译并提示用户明确需求。
                    【指定目标语言】（如：将XXX翻译成日语，或：翻译成日语：XXX）
                    1. 按用户要求翻译为指定目标语言。
                    2. 若目标语言为日语，同时需提供日文平假名读音。
                    - 翻译为日语时，确保准确、符合日语语法、保持适度礼貌但不过度，避免使用过于书面化汉字。
                    仅输出翻译后的内容，不输出任何额外信息。如用户指令不明确，拒绝翻译并提示用户明确需求。
                    """;

            ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                    .addSystemMessage(instruction)
                    .addUserMessage(userInputTxt)
                    .model(ChatModel.GPT_4_1)
                    .build();

            StringBuilder result = new StringBuilder();

            client.chat().completions().create(params).choices().stream()
                    .flatMap(choice -> choice.message().content().stream())
                    .forEach(result::append);
            return result.toString();

        } catch (Exception e) {
            log.error("[Translate] Error calling OpenAI API", e);
            return "翻译服务暂时不可用，请稍后重试。";
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
     * 异步处理翻译任务
     */
    @Async("translateTaskExecutor")
    public CompletableFuture<Void> processTranslationAsync(CommandRequestPayload payload) {
        try {
            String translationResult = translate(payload.getText().trim());

            // 通过 response_url 发送翻译结果
            sendWebhookResponse(payload.getResponse_url(), translationResult);

            log.info("[Translate] async translation completed and sent via webhook");
        } catch (Exception e) {
            log.error("[Translate] Error in async translation", e);
            // 发送错误消息
            sendWebhookResponse(payload.getResponse_url(), "翻译服务暂时不可用，请稍后重试。");
        }

        return CompletableFuture.completedFuture(null);
    }
}