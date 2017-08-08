package self.srr.m2g.biz;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import org.springframework.stereotype.Service;
import self.srr.m2g.constants.CliArgs;
import self.srr.m2g.utils.CliUtils;

/**
 * Created by Sharuru on 2017/08/08.
 */
@Service
public class CliBiz {


    public CommandLine initializeCli(String[] args) throws ParseException {

        CommandLine cli = null;

        try {
            cli = CliUtils.initializeCLI(args);

            // passed
            System.out.println("-----");
            System.out.println("PARAMETER CHECK PASSED.");
            System.out.println("-----");
            System.out.println("Reading file at: " + cli.getOptionValue(CliArgs.FILE_PATH));
        } catch (ParseException e) {
            System.out.println("-----");
            System.out.println("ILLEGAL_INPUT_DETECTED.");
            System.out.println(e.getMessage());
        }
        return cli;
    }
}
