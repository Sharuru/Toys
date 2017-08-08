package self.srr.m2g;

import lombok.extern.slf4j.Slf4j;
import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.ResourceAssignment;
import net.sf.mpxj.Task;
import net.sf.mpxj.TaskField;
import net.sf.mpxj.mpp.MPPReader;
import net.sf.mpxj.reader.ProjectReader;
import org.apache.commons.cli.CommandLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import self.srr.m2g.biz.CliBiz;
import self.srr.m2g.biz.MppBiz;
import self.srr.m2g.constants.CliArgs;
import self.srr.m2g.model.MppBizParam;
import self.srr.m2g.model.TaskModel;
import self.srr.m2g.utils.CliUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Sharuru on 2017/08/08.
 */
@Component
public class MainLogic {

    private static final SimpleDateFormat S_D_F = new SimpleDateFormat("yyyyMMdd");

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

            // get task lists
            List<TaskModel> tasks = mppBiz.getTasks(new MppBizParam(cli));

            // send to gitlab

            System.out.println("-----");
            System.out.println("OPERATION_FINISHED.");

        } catch (Exception e) {
            System.out.println("-----");
            System.out.println("OPERATION_ABORTED.");
        }
    }
}
