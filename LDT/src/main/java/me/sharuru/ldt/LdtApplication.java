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
     * The output path of the diagnostic result
     */
    @Value("${outputPath:}")
    String outputPath;

    /**
     * The output name of the diagnostic result
     */
    @Value("${outputFilename:}")
    String outputFileName;

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
        inputPath = inputPath.isEmpty() ? System.getProperty("user.dir") + File.separator + DEFAULT_INPUT_FILENAME : inputPath;
        log.info("inputPath: {}", inputPath);
        outputPath = outputPath.isEmpty() ? System.getProperty("user.dir") : outputPath;
        if(Files.isDirectory(new File(outputPath).toPath())){
            outputPath = outputPath +  File.separator + (outputFileName.isEmpty() ? DEFAULT_RESULT_FILENAME : outputFileName);
        }
        log.info("outputPath: {}", outputPath);
        List<MetaTaskInfo> testSuites = ldtCore.diagnostic(inputPath);
        if(!testSuites.isEmpty()){
            ldtCore.write(testSuites, outputPath);
        }
    }
}
