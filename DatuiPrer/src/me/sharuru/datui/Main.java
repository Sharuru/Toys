package me.sharuru.datui;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.logging.Logger;

public class Main {

    static Logger logger = Logger.getLogger(Main.class.toString());

    static final String PR_BASE_URL = "https://herr-ashi-service.herokuapp.com/api/search?image_label_nana=false&image_label_socks_color=black&image_label_socks_height=knee";

    public static void main(String[] args) throws IOException, InterruptedException {
        logger.info("DatuiPrer is launching...");

        HttpClient httpClient = HttpClient.newBuilder().build();
        Gson gson = new Gson();
        HttpRequest httpRequest;
        HttpResponse<String> httpResponse;
        String nextQueryIndex = "";
        String responseString;
        List<DatuiResponse> datuiResponses;

        // TODO recover task from any point
        logger.info("Requesting the first batch...");

        httpRequest = HttpRequest.newBuilder().uri(URI.create(PR_BASE_URL)).GET().build();
        httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        responseString = httpResponse.body();
        logger.info("The first batch response: " + responseString);

        datuiResponses = gson.fromJson(responseString, new TypeToken<List<DatuiResponse>>() {
        }.getType());

        for (int i = 0; i < datuiResponses.size(); i++) {
            writeToFile(datuiResponses.get(i));
            if (i == datuiResponses.size() - 1) {
                nextQueryIndex = datuiResponses.get(i).getCreatedAt();
            }
        }

        if (!"".equals(nextQueryIndex)) {
            while (!datuiResponses.isEmpty()){
                httpRequest = HttpRequest.newBuilder().uri(URI.create(PR_BASE_URL.concat("&from=" + (Long.parseLong(nextQueryIndex) - 1)))).GET().build();
                httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
                responseString = httpResponse.body();
                datuiResponses = gson.fromJson(responseString, new TypeToken<List<DatuiResponse>>() {
                }.getType());
                for (int i = 0; i < datuiResponses.size(); i++) {
                    writeToFile(datuiResponses.get(i));
                    if (i == datuiResponses.size() - 1) {
                        nextQueryIndex = datuiResponses.get(i).getCreatedAt();
                    }
                }
            }
        }

    }

    private static void writeToFile(DatuiResponse line) {
        try {
            logger.info("Appended: " + line.getId() + " / " + line.getS3ImageId());
            Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
            File rawOutputFile = new File(path + File.separator + "datui_raw_output.log");
            File downloadOutputFile = new File(path + File.separator + "datui_download_output.log");
            rawOutputFile.createNewFile();
            downloadOutputFile.createNewFile();
            Files.write(rawOutputFile.toPath(), line.toString().concat("\n").getBytes(), StandardOpenOption.APPEND);
            Files.write(downloadOutputFile.toPath(), line.getImageUrl().concat("\n").getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
