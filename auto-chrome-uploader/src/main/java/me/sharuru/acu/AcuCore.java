package me.sharuru.acu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.List;

@Slf4j
@Component
public class AcuCore {

    @Value("${acu.apiEndpoint}")
    private String apiEndpoint;

    @Value("${acu.token}")
    private String accessToken;

    @Value("${acu.channels}")
    private String[] notifyChannels;

    @Value("${acu.downloadPath}")
    private String downloadPath;

    private String chromeVersion;

    private static final String CHROME_VERSION_CHECK_URL = "https://omahaproxy.appspot.com/win";

    private static final String CHROME_DOWNLOAD_URL = "https://dl.google.com/tag/s/appguid%3D%7B8A69D345-D564-463C-AFF1-A69D9E530F96%7D%26iid%3D%7BD47E522A-C863-21C2-27BC-3D70BAC9AEA9%7D%26lang%3Dja%26browser%3D5%26usagestats%3D1%26appname%3DGoogle%2520Chrome%26needsadmin%3Dprefers%26ap%3Dx64-stable-statsdef_1%26installdataindex%3Dempty/chrome/install/ChromeStandaloneSetup64.exe";

    public void main() {
        log.info("Starting ACU cycle...");

        log.info("{}, {}, {}, {}", accessToken, notifyChannels, downloadPath, StringUtils.hasText(downloadPath));
        downloadChrome();
        try {
            sendNotification();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    public boolean downloadChrome() {
        File versionMarkFile = Path.of(System.getProperty("user.dir") + File.separator + "ACU.mark").toFile();
        if (!versionMarkFile.exists()) {
            log.warn("ACU.mark file is not found under {}, skipping...", versionMarkFile.toPath());
            return false;
        }

        try {
            List<String> markLines = Files.readAllLines(versionMarkFile.toPath());
            if (!markLines.isEmpty()) {
                String previousChromeVersion = markLines.get(0).trim();
                if (!previousChromeVersion.isEmpty()) {
                    log.info("Previous Chrome version is: {}", previousChromeVersion);
                    HttpRequest httpRequest = HttpRequest.newBuilder(new URI(CHROME_VERSION_CHECK_URL))
                            .GET()
                            .timeout(Duration.ofSeconds(5))
                            .build();
                    HttpClient httpClient = HttpClient.newBuilder()
                            .version(HttpClient.Version.HTTP_2)
                            .proxy(ProxySelector.of(new InetSocketAddress("127.0.0.1", 7890)))
                            .connectTimeout(Duration.ofSeconds(5))
                            .build();
                    HttpResponse<String> versionResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
                    String latestChromeVersion = versionResponse.body().trim();
                    if (!latestChromeVersion.isEmpty()) {
                        log.info("Latest Chrome version is: {}", latestChromeVersion);
                        if (latestChromeVersion.equals(previousChromeVersion)) {
                            log.info("The version is already up-to-date, skipping...");
                        } else {
                            log.info("Upgrade needed, downloading...");

                            httpRequest = HttpRequest.newBuilder(new URI(CHROME_DOWNLOAD_URL))
                                    .GET()
                                    .timeout(Duration.ofSeconds(5))
                                    .build();

                            File chromeFile = Path.of(StringUtils.hasText(downloadPath) ? downloadPath : System.getProperty("user.dir") + File.separator + "ChromeStandaloneSetup64_v" + latestChromeVersion + ".exe").toFile();
                            HttpResponse<InputStream> downloadResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofInputStream());
                            Files.copy(downloadResponse.body(), chromeFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                            log.info("ChromeStandaloneSetup64_v{}.exe download completed to {}", latestChromeVersion, chromeFile.toPath());
                            chromeVersion = "ChromeStandaloneSetup64_v"+ latestChromeVersion + ".exe";

                            Files.deleteIfExists(versionMarkFile.toPath());
                            Files.writeString(versionMarkFile.toPath(), latestChromeVersion);

                            return true;
                        }
                    } else {
                        log.info("Version check failed, skipping...");
                    }
                } else {
                    log.warn("Something not right, skipping...");
                }
            }
        } catch (InterruptedException | IOException | URISyntaxException e) {
            log.error("Error happened in cycle, skipping...\n{}", e.getMessage());
        }
        return false;
    }

    public void sendNotification() throws IOException, InterruptedException {

        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        String message = "Chrome 离线安装程序（"+ chromeVersion + "）已上传至下载中心。" ;

        String json = "{" +
                "\"channel_id\":\"" + notifyChannels[0] + "\"," +
                "\"message\":\"" + message + "\"" +
                "}";

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .uri(URI.create(apiEndpoint))
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        log.info("Response with status code: {}\n{}", response.statusCode(), response.body());

    }

}
