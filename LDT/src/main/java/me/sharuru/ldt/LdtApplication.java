package me.sharuru.ldt;

import lombok.extern.slf4j.Slf4j;
import me.sharuru.ldt.core.LdtCore;
import me.sharuru.ldt.core.common.MetaTaskInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.info.BuildProperties;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Main entrypoint of the LDT
 */
@Slf4j
@SpringBootApplication
public class LdtApplication implements CommandLineRunner {

    /**
     * Main LDT processes
     */
    @Autowired
    LdtCore ldtCore;

    /**
     * The path of the input file for diagnostic
     */
    @Value("${inputPath:}")
    String inputPath;

    /**
     * The paths of the input file for diagnostic
     */
    @Value("${mixedInputPaths:}")
    String mixedInputPaths;

    /**
     * The output path of the diagnostic result
     */
    @Value("${outputPath:}")
    String outputPath;

    /**
     * The output name of the diagnostic result
     */
    @Value("${outputFileName:}")
    String outputFileName;

    /**
     * Build properties
     */
    @Autowired
    BuildProperties buildProperties;

    private static final String DEFAULT_INPUT_FILENAME = "console-output.log";
    private static final String DEFAULT_RESULT_FILENAME = "diagnostic-result.csv";

    public static void main(String[] args) {
        SpringApplication.run(LdtApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Starting LDT v{}...", buildProperties.getVersion());

        // decide the input path
        List<String> inputPaths = new ArrayList<>(0);
        if (!mixedInputPaths.isEmpty()) {
            log.info("`mixedInputPaths` is provided, this is an EXPERIMENTAL function.");
            log.info("inputPath: {}", mixedInputPaths);
            inputPaths.addAll(Arrays.asList(mixedInputPaths.split(",")));
        } else {
            inputPath = inputPath.isEmpty() ? System.getProperty("user.dir") + File.separator + DEFAULT_INPUT_FILENAME : inputPath;
            log.info("inputPath: {}", inputPath);
            inputPaths.add(inputPath);
        }

        // decide the output path
        outputPath = outputPath.isEmpty() ? System.getProperty("user.dir") : outputPath;
        if (Files.isDirectory(new File(outputPath).toPath())) {
            outputPath = outputPath + File.separator + (outputFileName.isEmpty() ? DEFAULT_RESULT_FILENAME : outputFileName);
        }
        log.info("outputPath: {}", outputPath);

        // LDT logic start
        List<MetaTaskInfo> testSuites = ldtCore.diagnostic(inputPaths);

        // not empty, write to file
        if (!testSuites.isEmpty()) {
            ldtCore.write(testSuites, outputPath);
        }

        log.info("LDT finished");
    }
}
