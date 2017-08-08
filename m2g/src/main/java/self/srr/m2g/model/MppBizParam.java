package self.srr.m2g.model;

import lombok.Data;
import org.apache.commons.cli.CommandLine;
import self.srr.m2g.constants.CliArgs;

/**
 * Created by Sharuru on 2017/08/08.
 */
@Data
public class MppBizParam {

    private String filePath;

    private String startDtStr;

    private String finishDtStr;

    private String targetResourceName;

    public MppBizParam(CommandLine cli) {
        this.filePath = cli.getOptionValue(CliArgs.FILE_PATH);
        this.startDtStr = cli.getOptionValue(CliArgs.START_DATE);
        this.finishDtStr = cli.getOptionValue(CliArgs.END_DATE);
        this.targetResourceName = cli.getOptionValue(CliArgs.TARGET_RESOURCES);
    }
}


