package me.sharuru.matterslash.service;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TranslateService {

    @Value("${slash.translate.openAiKey}")
    private String openAiKey;

    @Value("${slash.translate.apiBaseUrl}")
    private String apiBaseUrl;

    public String translate(final String userInput) {

        String[] userInputArgs = userInput.split("\\s+");

        // 检查输入是否为空
        if (userInputArgs.length == 0 || userInput.trim().isEmpty()) {
            return "输入的命令无法识别，请确认。如需帮助，可使用 `help` 命令。";
        }

        String userCommand = userInputArgs[0].toLowerCase();

        // 处理 help 命令
        if ("help".equals(userCommand)) {
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

        // 检查是否有翻译内容
        if (userInputArgs.length < 2) {
            return "请输入要翻译的内容。如需帮助，可使用 `help` 命令。";
        }

        String translateText = Arrays.stream(userInputArgs, 1, userInputArgs.length).collect(Collectors.joining(" "));
        log.info("[Translate] targetLanguage: {}, translateText: {}", userCommand, translateText);

        try {
            // 创建官方SDK客户端，支持自定义API URL
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
                    【指定目标语言】（如：将XXX翻译成日语，或：翻译成日语：XXX）
                    1. 按用户要求翻译为指定目标语言。
                    2. 若目标语言为日语，同时需提供日文平假名读音。
                    - 翻译为日语时，确保准确、符合日语语法、保持适度礼貌但不过度，避免使用过于书面化汉字。
                    仅输出翻译后的内容，不输出任何额外信息。如用户指令不明确，拒绝翻译并提示用户明确需求。
                    开始前，简要列出本次翻译的主要步骤（3-5条，概括性说明），确保任务完整，并据此执行。
                    """;

            ResponseCreateParams params = ResponseCreateParams.builder()
                    .input(translateText)
                    .model(ChatModel.GPT_4_1)
                    .instructions(instruction)
                    .build();
            Response response = client.responses().create(params);

            StringBuilder result = new StringBuilder();
            response.output().stream()
                    .flatMap(item -> item.message().stream())
                    .flatMap(message -> message.content().stream())
                    .flatMap(content -> content.outputText().stream())
                    .forEach(outputText -> result.append(outputText.text()));

            return result.toString();

        } catch (Exception e) {
            log.error("[Translate] Error calling OpenAI API", e);
            return "翻译服务暂时不可用，请稍后重试。";
        }
    }
}