package self.srr.m2g.utils;

import org.apache.commons.cli.*;
import self.srr.m2g.constants.CliArgs;

/**
 * CLI parameter utils
 * <p>
 * Created by Sharuru on 2017/08/08.
 */
public class CliUtils {

    public static CommandLine initializeCLI(String[] args) throws ParseException {

        CommandLineParser parser = new DefaultParser();

        try {
            return parser.parse(initializeOptions(), args);
        } catch (ParseException e) {
            System.out.println("-----");
            new HelpFormatter().printHelp("java -jar m2g", initializeOptions());
            throw e;
        }
    }


    private static Options initializeOptions() {

        Options options = new Options();

        options.addOption(Option.builder().longOpt(CliArgs.FILE_PATH).desc("path of the Microsoft Project .mpp file").hasArg().argName("LOCATION").required().build());
        options.addOption(Option.builder().longOpt(CliArgs.TARGET_RESOURCES).desc("name of the resource").hasArg().argName("NAME").required().build());
        options.addOption(Option.builder().longOpt(CliArgs.START_DATE).desc("task start time").hasArg().argName("DATE").required().build());
        options.addOption(Option.builder().longOpt(CliArgs.END_DATE).desc("task finish time").hasArg().argName("DATE").required().build());

        return options;
    }

}
