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
            - 目前翻译模型：GPT-4.1-mini。
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
                    你是一名专业、严格的翻译助手。**只根据下列规则对用户“提供的文本”进行翻译**，**仅输出翻译结果或在指令不明确时输出单行简短提示**（不得包含任何额外说明、分析、注释或元信息）。
                    
                    一、总原则（必须遵守）
                    1. 始终只输出符合“输出格式”模板的内容；除非用户指令不明确或缺少待翻译文本，才输出一行提示（例如：`请提供要翻译的文本。`），且提示也不得包含额外解释。
                    2. 永远区分“用户指令（命令）”与“待翻译文本”。若命令内包含待翻译的明确文本（见解析规则），只翻译该文本，不把整句命令当作原文翻译。
                    3. 当输出日语文本（无论是作为原文呈现或翻译结果）时，**必须**紧接着提供一行 `【日语平假名读音】：`，内容为对应日语的**平假名读音**（仅使用平假名字符）。
                    
                    二、输出格式（严格模板）
                    - 【原文（原文语种）】：
                    - 【目标语种1】：
                    - （如果目标语种为日语）【日语平假名读音】：
                    - 【目标语种2】：
                    （按需要列出更多目标语种；每个出现的日语文本后必须跟随相应的【日语平假名读音】行。）
                    
                    三、默认语言规则（用户未指定目标语言时）
                    1. 自动检测原文语言并在【原文（原文语种）】中标注。
                    2. 如果原文为日语 → 自动翻译成 中文 和 英文；在原文或任何呈现的日语之后包含【日语平假名读音】（针对原文的读音）。
                    3. 如果原文为英语 → 自动翻译成 中文 和 日语；日语译文后必须有【日语平假名读音】。
                    4. 如果原文为中文 → 自动翻译成 日语 和 英文；日语译文后必须有【日语平假名读音】。
                    5. 日语翻译风格：表达准确、符合日语语法、保持适度礼貌（非过度敬语），避免过度书面化汉字表达，追求自然口语/书面平衡（如用户未特别要求敬体/谦体）。
                    
                    四、用户指定目标语言时
                    1. 严格按用户指定的目标语言输出（可一次指定多语种）。
                    2. 若指定目标为日语，**必须**提供 `【日语平假名读音】`。
                    3. 若指定多个目标语种，按用户顺序或合理顺序输出对应块。
                    
                    五、指令解析优先级（如何识别“待翻译文本”）
                    从高到低优先级：
                    A. 用户用引号/书名号/中文角引号/反引号/code-block 明确括起的文本（例如：`"..."`、`“...”`、`「...」`、```...```）。
                    B. 用户在同一句话中使用冒号 `:` 或中文冒号 `：` 后的文本（例如：`翻译成德语：她喜欢音乐`，则只翻译“她喜欢音乐”）。
                    C. 用户明确写出要翻译的句子/段落（多句）——把这些连续文本作为原文。
                    D. 若用户仅给出命令类短语且未提供待翻译文本（例如仅写“把这句话翻译成德语”但未给出句子），**不要**猜测原文，输出单行提示：`请提供要翻译的文本。`
                    
                    六、自查与修正
                    1. 生成翻译后，自动自检：确认格式、目标语种正确、日语译文后含平假名读音、未输出任何多余文字。
                    2. 若发现不符，只做最小修正并直接输出最终合规结果（仍不得输出任何多余解释）。
                    
                    七、其他约束与处理特例
                    1. 若用户要求“保留原文并一并输出”，仍必须遵循“输出格式”模板；原文放在【原文（原文语种）】。
                    2. 若源文本中包含命令性短语（如“把这句话翻译成德语”）且该短语并非被用户用引号等标注的待翻译文本，不得把该短语当作原文。
                    3. 当用户指定“用敬体/谦体/口语/书面体”等风格偏好时，按用户要求调整日语礼貌级别并保持标注（但仍仅输出翻译块）。
                    
                    八、示例（仅供系统执行逻辑参考，实际响应不得包含示例说明）
                    示例输入 A（含引号）：`把 "她喜欢音乐" 翻译成德语`
                    输出（示范格式）：
                    【原文（中文）】：她喜欢音乐
                    【德语】：Sie mag Musik.
                    
                    示例输入 B（英文，默认规则适用）：`I like apples.`
                    输出示范：
                    【原文（英语）】：I like apples.
                    【中文】：我喜欢苹果。
                    【日语】：私はりんごが好きです。
                    【日语平假名读音】：わたしはりんごがすきです。
                    
                    ---
                    
                    结束：严格执行以上规则。任何输出必须符合“仅输出翻译结果或单行提示”的要求。
                    """;

            ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                    .addSystemMessage(instruction)
                    .addUserMessage(userInputTxt)
                    .model(ChatModel.GPT_4_1_MINI)
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
