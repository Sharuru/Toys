package me.sharuru.acu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.*;
import java.time.Duration;
import java.util.List;

@Slf4j
@Component
public class AcuCore {

    public void main() {
        log.info("Starting ACU cycle...");

        File versionMarkFile = Path.of(System.getProperty("user.dir") + File.separator + "ACU.mark").toFile();
        if (!versionMarkFile.exists()) {
            log.warn("ACU.mark file is not found under {}, skipping...", versionMarkFile.toPath());
            return;
        }

        try {
            List<String> markLines = Files.readAllLines(versionMarkFile.toPath());
            if (!markLines.isEmpty()) {
                String previousChromeVersion = markLines.get(0).trim();
                if (!previousChromeVersion.isEmpty()) {
                    log.info("Previous Chrome version is: {}", previousChromeVersion);
                    HttpRequest httpRequest = HttpRequest.newBuilder(new URI("https://omahaproxy.appspot.com/win"))
                            .GET()
                            .timeout(Duration.ofSeconds(5))
                            .build();
                    HttpClient httpClient = HttpClient.newBuilder()
                            .version(HttpClient.Version.HTTP_2)
                            //.proxy(ProxySelector.of(new InetSocketAddress("127.0.0.1", 7890)))
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

                            httpRequest = HttpRequest.newBuilder(new URI("https://dl.google.com/tag/s/appguid%3D%7B8A69D345-D564-463C-AFF1-A69D9E530F96%7D%26iid%3D%7BD47E522A-C863-21C2-27BC-3D70BAC9AEA9%7D%26lang%3Dja%26browser%3D5%26usagestats%3D1%26appname%3DGoogle%2520Chrome%26needsadmin%3Dprefers%26ap%3Dx64-stable-statsdef_1%26installdataindex%3Dempty/chrome/install/ChromeStandaloneSetup64.exe"))
                                    .GET()
                                    .timeout(Duration.ofSeconds(5))
                                    .build();
                            File chromeFile = Path.of(System.getProperty("user.dir") + File.separator + "ChromeStandaloneSetup64_v" + latestChromeVersion + ".exe").toFile();
                            HttpResponse<InputStream> downloadResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofInputStream());
                            Files.copy(downloadResponse.body(), chromeFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                            log.info("ChromeStandaloneSetup64_v{}.exe download completed to {}", latestChromeVersion, chromeFile.toPath());

                            Files.deleteIfExists(versionMarkFile.toPath());
                            Files.writeString(versionMarkFile.toPath(), latestChromeVersion);
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
    }
}
