package self.srr.m2g.model;

import lombok.Data;
import org.apache.commons.cli.CommandLine;
import org.sqlite.util.StringUtils;
import self.srr.m2g.constants.CliArgs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Sharuru on 2017/08/08.
 */
@Data
public class MppBizParam {

    private String filePath;

    private String startDtStr;

    private String finishDtStr;

    private String targetResourceName;

    private List<String> targetTaskIds = new ArrayList<>();

    public MppBizParam(CommandLine cli) {
        this.filePath = cli.getOptionValue(CliArgs.FILE_PATH);
        this.startDtStr = cli.getOptionValue(CliArgs.START_DATE);
        this.finishDtStr = cli.getOptionValue(CliArgs.END_DATE);
        this.targetResourceName = cli.getOptionValue(CliArgs.TARGET_RESOURCES);
        if (cli.hasOption(CliArgs.TARGET_TASK_ID) && !cli.getOptionValue(CliArgs.TARGET_TASK_ID).isEmpty()) {
            this.targetTaskIds = Arrays.asList(cli.getOptionValue(CliArgs.TARGET_TASK_ID).split(","));
            System.out.println("-----");
            System.out.println("GOT SPECIFIC TASK SELECTION: " + StringUtils.join(this.targetTaskIds, ", "));
        }
    }
}


