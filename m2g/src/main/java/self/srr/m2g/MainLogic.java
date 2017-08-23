package self.srr.m2g;

import net.sf.mpxj.Task;
import org.apache.commons.cli.CommandLine;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import self.srr.m2g.biz.CliBiz;
import self.srr.m2g.biz.MppBiz;
import self.srr.m2g.biz.PushBiz;
import self.srr.m2g.constants.CliArgs;
import self.srr.m2g.model.MppBizParam;
import self.srr.m2g.model.PushBizParam;
import self.srr.m2g.model.TaskModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sharuru on 2017/08/08.
 */
@Component
public class MainLogic {

    private static final SimpleDateFormat S_D_F = new SimpleDateFormat("yyyyMMdd");

    private static boolean pushFlg = false;

    @Autowired
    CliBiz cliBiz;

    @Autowired
    MppBiz mppBiz;

    @Autowired
    PushBiz pushBiz;

    /**
     * main method
     *
     * @param args user input
     */
    void run(String[] args) {
        System.out.println("-----");
        System.out.println("STARTING.");

        try {
            // parse cli args
            CommandLine cli = cliBiz.initializeCli(args);

            // output task type
            if (cli.hasOption(CliArgs.TASK_TYPE)) {
                if (cli.getOptionValue(CliArgs.TASK_TYPE).equals(CliArgs.TASK_TYPE_CHECK)) {
                    System.out.println("Task type: TASK " + CliArgs.TASK_TYPE_CHECK);
                } else if (cli.getOptionValue(CliArgs.TASK_TYPE).equals(CliArgs.TASK_TYPE_PUSH)) {
                    pushFlg = true;
                    System.out.println("Task type: TASK " + CliArgs.TASK_TYPE_PUSH);
                } else if (cli.getOptionValue(CliArgs.TASK_TYPE).equals(CliArgs.TASK_TYPE_LOCAL)) {
                    System.out.println("Task type: TASK " + CliArgs.TASK_TYPE_LOCAL);
                } else {
                    System.out.println("Task type not exist! Using default: TASK " + CliArgs.TASK_TYPE_LOCAL);
                }
            } else {
                System.out.println("Task type not set! Using default: TASK " + CliArgs.TASK_TYPE_LOCAL);
            }

            // passed
            System.out.println("-----");
            System.out.println("PARAMETER CHECK PASSED.");

            // get task lists
            List<TaskModel> tasks = mppBiz.getTasks(new MppBizParam(cli));

            System.out.println("-----");
            System.out.println("Filtered task(s) count: " + tasks.size());

            // need net connection
            if (pushFlg) {
                // send to gitlab
                System.out.println("-----");
                System.out.println("Sending filtered tasks to Gitlab...");

                pushBiz.push(new PushBizParam(cli), tasks);
            }

            System.out.println("-----");
            System.out.println("OPERATION_FINISHED.");

        } catch (Exception e) {
            System.out.println("-----");
            System.out.println("OPERATION_ABORTED.");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
