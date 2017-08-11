package self.srr.m2g;

import org.apache.commons.cli.CommandLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import self.srr.m2g.biz.CliBiz;
import self.srr.m2g.biz.MppBiz;
import self.srr.m2g.constants.CliArgs;
import self.srr.m2g.model.MppBizParam;
import self.srr.m2g.model.TaskModel;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Sharuru on 2017/08/08.
 */
@Component
public class MainLogic {

    private static final SimpleDateFormat S_D_F = new SimpleDateFormat("yyyyMMdd");

    private static boolean checkFlg = false;
    private static boolean pushFlg = false;

    @Autowired
    CliBiz cliBiz;

    @Autowired
    MppBiz mppBiz;

    public void run(String[] args) {
        System.out.println("-----");
        System.out.println("STARTING.");

        try {
            // parse cli args
            CommandLine cli = cliBiz.initializeCli(args);

            // output task type
            if (cli.hasOption(CliArgs.TASK_TYPE)) {
                if (cli.getOptionValue(CliArgs.TASK_TYPE).equals(CliArgs.TASK_TYPE_CHECK)) {
                    checkFlg = true;
                    System.out.println("Task type: TASK " + CliArgs.TASK_TYPE_CHECK);
                } else if (cli.getOptionValue(CliArgs.TASK_TYPE).equals(CliArgs.TASK_TYPE_PUSH)) {
                    pushFlg = true;
                    System.out.println("Task type: TASK " + CliArgs.TASK_TYPE_PUSH);
                } else if (cli.getOptionValue(CliArgs.TASK_TYPE).equals(CliArgs.TASK_TYPE_LOCAL)) {
                    pushFlg = true;
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
            }

            // TODO START

//            CloseableHttpClient httpClient = HttpClients.createDefault();
//
//            try {
//                HttpPost httpPost = new HttpPost("http://172.17.229.212:8800/api/v4/projects/116/issues");
//
//                for (TaskModel t : tasks) {
//
//                    httpPost.setHeader("PRIVATE-TOKEN","1XiFxtaYKgy_SbQ-zCh8");
//
//                    List<NameValuePair> params = new ArrayList<>();
//
//                    params.add(new BasicNameValuePair("title", t.getTaskId() + " / " + t.getParentTaskName() + " / " + t.getFunctionName() + " / " + t.getTaskName() + " / " + t.getOrigTaskType()));
//                    params.add(new BasicNameValuePair("description", t.getTaskName()));
//                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                    params.add(new BasicNameValuePair("due_date", sdf.format(t.getFinishDate())));
//                    params.add(new BasicNameValuePair("charset", "UTF-8"));
//
//                    httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
//                    HttpResponse response = HttpClients.createDefault().execute(httpPost);
//
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }


            // TODO END


            System.out.println("-----");
            System.out.println("OPERATION_FINISHED.");

        } catch (Exception e) {
            System.out.println("-----");
            System.out.println("OPERATION_ABORTED.");
            System.out.println(e.getMessage());
        }
    }
}
