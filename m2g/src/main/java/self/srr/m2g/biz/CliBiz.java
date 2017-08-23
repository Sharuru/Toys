package self.srr.m2g.biz;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import org.springframework.stereotype.Service;
import self.srr.m2g.utils.CliUtils;

/**
 * Created by Sharuru on 2017/08/08.
 */
@Service
public class CliBiz {

    /**
     * get cli instances
     *
     * @param args user input
     * @return cli instances
     * @throws ParseException parse exception
     */
    public CommandLine initializeCli(String[] args) throws ParseException {

        CommandLine cli = null;

        try {
            cli = CliUtils.initializeCLI(args);
        } catch (ParseException e) {
            System.out.println("-----");
            System.out.println("ILLEGAL_INPUT_DETECTED.");
            System.out.println(e.getMessage());
        }

        return cli;
    }
}
