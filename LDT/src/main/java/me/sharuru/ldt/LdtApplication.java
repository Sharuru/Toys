package me.sharuru.ldt;

import lombok.extern.slf4j.Slf4j;
import me.sharuru.ldt.core.LdtCore;
import me.sharuru.ldt.core.common.MetaTaskInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@Slf4j
@SpringBootApplication
// TODO
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

    public static void main(String[] args) {
        SpringApplication.run(LdtApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Starting LDT...");
        log.info("logPath: {}", inputPath);
        if(inputPath.isEmpty()){
            log.error("Log path was not determined.");
            System.exit(-1);
        }
        List<MetaTaskInfo> testSuites = ldtCore.diagnostic(inputPath);
        if(!testSuites.isEmpty()){
            ldtCore.write(testSuites, outputPath, outputFileName);
        }
    }
}
