package me.sharuru.acu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
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
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

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

    @Value("${acu.markPath}")
    private String markPath;

    private String installerFilename;

    private static final String CHROME_VERSION_CHECK_URL = "https://omahaproxy.appspot.com/win";

    private static final String CHROME_DOWNLOAD_URL = "https://dl.google.com/tag/s/appguid%3D%7B8A69D345-D564-463C-AFF1-A69D9E530F96%7D%26iid%3D%7BD47E522A-C863-21C2-27BC-3D70BAC9AEA9%7D%26lang%3Dja%26browser%3D5%26usagestats%3D1%26appname%3DGoogle%2520Chrome%26needsadmin%3Dprefers%26ap%3Dx64-stable-statsdef_1%26installdataindex%3Dempty/chrome/install/ChromeStandaloneSetup64.exe";

    @Scheduled(cron = "0 0 9 * * *", zone = "Asia/Shanghai")
    public void main() {
        log.info("Starting ACU cycle...");
        boolean downloadStatus = false;
        boolean notifyStatus = false;
        downloadStatus = downloadChrome();
        if (downloadStatus)
            notifyStatus = sendNotification();
        log.info("Download status: {}, notify status: {}", downloadStatus, notifyStatus);
        log.info("ACU cycle finished.");
    }


    public boolean downloadChrome() {
        File versionMarkFile = Path.of((StringUtils.hasText(this.markPath) ? this.markPath : System.getProperty("user.dir")) + File.separator + "ACU.mark").toFile();
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
                            this.installerFilename = "ChromeStandaloneSetup64_v" + latestChromeVersion + ".exe";
                            File chromeFile = Path.of((StringUtils.hasText(this.downloadPath) ? this.downloadPath : System.getProperty("user.dir")) + File.separator + installerFilename).toFile();
                            HttpResponse<InputStream> downloadResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofInputStream());
                            Files.copy(downloadResponse.body(), chromeFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                            log.info("{} download completed to {}", this.installerFilename, chromeFile.toPath());

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

    public boolean sendNotification() {

        AtomicBoolean hasError = new AtomicBoolean(false);
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(10L))
                .build();
        String messageContext = "Chrome 离线安装程序（" + this.installerFilename + "）已上传至下载中心。";
        Arrays.stream(this.notifyChannels).forEach(channelId -> {

            String json = "{" +
                    "\"channel_id\":\"" + channelId + "\"," +
                    "\"message\":\"" + messageContext + "\"" +
                    "}";

            HttpRequest request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .uri(URI.create(this.apiEndpoint))
                    .header("Authorization", "Bearer " + this.accessToken)
                    .header("Content-Type", "application/json")
                    .build();

            try {
                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                log.info("Message sent to {}, response: {}, detail: \n{}", channelId, response.statusCode(), response.body());
            } catch (IOException | InterruptedException e) {
                log.error("Error happened...\n{}", e.getMessage());
                hasError.set(true);
            }
        });
        return !hasError.get();
    }

}
