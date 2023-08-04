package me.sharuru.matterslash.service;

import com.theokanning.openai.client.OpenAiApi;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TranslateService {

    @Value("${slash.translate.openAiKey}")
    private String openAiKey;

    @Value("${slash.translate.apiBaseUrl}")
    private String apiBaseUrl;

    @Value("${slash.translate.usingModel}")
    private String usingModel;

    Map<String, String> availableLanguage = Map.ofEntries(
            Map.entry("zh", "中文"),
            Map.entry("en", "英语"),
            Map.entry("jp", "日语")
    );

    public String translate(final String userInput) {

        String[] userInputArgs = userInput.split("\\s+");
        if (userInputArgs.length < 2 && !"help".equalsIgnoreCase(userInputArgs[0])) {
            return "输入的命令无法识别，请确认。如需帮助，可使用 `help` 命令。";
        }
        String targetLanguage = userInputArgs[0].toLowerCase();
        String translateText = Arrays.stream(userInputArgs, 1, userInputArgs.length).collect(Collectors.joining());
        log.info("[Translate] targetLanguage: {}, translateText: {}", targetLanguage, translateText);
        if ("help".equals(targetLanguage)) {
            return """
                    翻译功能（OpenAI）的帮助信息：
                    使用方法：输入 `/faiyp 语种 文本。`
                    `/faiyp zh こんにちは` 将日语こんにちは翻译成中文。
                    目前支持的语种如下：
                                       
                    | 语种代码     | 名称                     |
                    |:------------|:------------------------|
                    | zh          | 中文                     |
                    | en          | 英语                     |
                    | jp          | 日语                     |
                                        
                    生成式 AI 的翻译结果响应时间较长，长文本建议分段发送。
                    在上次请求没有返回、超时前，系统不接受新的请求。
                    翻译内容仅供参考。
                    """;
        }
        if (!availableLanguage.containsKey(targetLanguage)) {
            return "输入的语种无法识别，请确认。如需帮助，可使用 `help` 命令。";
        }
        targetLanguage = availableLanguage.get(targetLanguage);

        OkHttpClient client = OpenAiService.defaultClient(openAiKey, Duration.ofSeconds(60));
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(apiBaseUrl)
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create(OpenAiService.defaultObjectMapper()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        OpenAiService service = new OpenAiService(retrofit.create(OpenAiApi.class), client.dispatcher().executorService());
        List<ChatMessage> chatMessages = new ArrayList<>();
        String prompt = "" +
                "翻译成" + targetLanguage + "：" + translateText;
        chatMessages.add(new ChatMessage(ChatMessageRole.USER.value(), prompt));
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder().model(usingModel).messages(chatMessages).build();
        return "翻译结果：\n" + service.createChatCompletion(chatCompletionRequest).getChoices().get(0).getMessage().getContent();
    }
}