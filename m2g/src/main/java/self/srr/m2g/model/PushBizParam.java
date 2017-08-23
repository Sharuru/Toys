package self.srr.m2g.model;

import lombok.Data;
import org.apache.commons.cli.CommandLine;
import self.srr.m2g.constants.CliArgs;

/**
 * Created by Sharuru on 2017/08/23.
 */
@Data
public class PushBizParam {

    private String remoteUrl;

    private String remoteToken;

    public PushBizParam(CommandLine cli) {
        this.remoteUrl = cli.getOptionValue(CliArgs.ISSUE_LINK);
        this.remoteToken = cli.getOptionValue(CliArgs.AUTH_TOKEN);
    }

}
