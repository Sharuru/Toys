package self.srr.m2g.utils;

import org.apache.commons.cli.*;
import self.srr.m2g.constants.CliArgs;

/**
 * CLI parameter utils
 * <p>
 * Created by Sharuru on 2017/08/08.
 */
public class CliUtils {

    /**
     * get cli instances
     *
     * @param args user input
     * @return cli instances
     * @throws ParseException parse exception
     */
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

    /**
     * initialize argument options
     *
     * @return argument options
     */
    private static Options initializeOptions() {

        Options options = new Options();

        options.addOption(Option.builder().longOpt(CliArgs.FILE_PATH).desc("path of the Microsoft Project .mpp file").hasArg().argName("LOCATION").required().build());
        options.addOption(Option.builder().longOpt(CliArgs.TARGET_RESOURCES).desc("name of the resource").hasArg().argName("NAME").required().build());
        options.addOption(Option.builder().longOpt(CliArgs.START_DATE).desc("task start time").hasArg().argName("DATE").required().build());
        options.addOption(Option.builder().longOpt(CliArgs.END_DATE).desc("task finish time").hasArg().argName("DATE").required().build());
        options.addOption(Option.builder().longOpt(CliArgs.TASK_TYPE).desc(
                "setting single task\n" +
                        CliArgs.TASK_TYPE_LOCAL + ": output local .mpp file info \n" +
                        CliArgs.TASK_TYPE_CHECK + ": diff local tasks with remote \n" +
                        CliArgs.TASK_TYPE_PUSH + ": after TASK LOCAL, TASK CHECK, push diffed tasks to remote \n" +
                        "default task is: TASK LOCAL only"
        ).hasArg().argName("TASK").build());
        options.addOption(Option.builder().longOpt(CliArgs.ISSUE_LINK).desc("address of remote issues").hasArg().argName("ADDRESS").required().build());
        options.addOption(Option.builder().longOpt(CliArgs.AUTH_TOKEN).desc("token for authentication").hasArg().argName("TOKEN").required().build());

        return options;
    }

}
