package self.srr.m2g.biz;

import net.sf.mpxj.*;
import net.sf.mpxj.mpp.MPPReader;
import net.sf.mpxj.reader.ProjectReader;
import org.springframework.stereotype.Service;
import self.srr.m2g.constants.CliArgs;
import self.srr.m2g.model.MppBizParam;
import self.srr.m2g.model.TaskModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Sharuru on 2017/08/08.
 */
@Service
public class MppBiz {

    private static final SimpleDateFormat S_D_F = new SimpleDateFormat("yyyyMMdd");

    public List<TaskModel> getTasks(MppBizParam param) {

        List<TaskModel> tasks = new ArrayList<>();
        ProjectFile project = null;
        Date opStartDt = null;
        Date opEndDt = null;

        // get MPP file
        try {
            System.out.println("-----");
            System.out.println("Reading file at: " + param.getFilePath());
            project = getMppFile(param.getFilePath());
            System.out.println("-----");
            System.out.println("Get " + project.getAllTasks().size() + " task(s) under project.");
        } catch (MPXJException e) {
            System.out.println("-----");
            System.out.println("MPXJ_EXCEPTION_DETECTED.");
            System.out.println(e.getMessage());
        }

        // set filter date
        try {
            opStartDt = S_D_F.parse(param.getStartDtStr());
            opEndDt = S_D_F.parse(param.getFinishDtStr());
        } catch (ParseException e) {
            System.out.println("-----");
            System.out.println("PARSE_EXCEPTION_DETECTED.");
            System.out.println(e.getMessage());
        }

        // loop & return trimmed tasks
        for (Task task : project.getAllTasks()) {
            if (task.getName() != null) {   // illegal task check
                if (task.getStart().after(opStartDt) && task.getStart().before(opEndDt)) {
                    if (task.getOutlineLevel() == 3) {  // only fetch sub tasks
                        if (task.getResourceAssignments() != null) {  // have resources

                            String resourceNames = "";

                            for (ResourceAssignment resourceAssignment : task.getResourceAssignments()) {
                                resourceNames = resourceAssignment.getResource().getName().trim() + ",";
                                resourceNames = resourceNames.substring(0, resourceNames.length() - 1);
                            }

                            if (resourceNames.contains(param.getTargetResourceName())) {
                                System.out.println("-----");
                                System.out.println("Found target task: " + task.getName());
                                System.out.println("Parent task: " + task.getParentTask().getName());

                                Task parentTask = task.getParentTask();

                                String adminId = parentTask.getCachedValue(TaskField.TEXT1) == null ? "N/A" : parentTask.getCachedValue(TaskField.TEXT1).toString();
                                String taskId = parentTask.getCachedValue(TaskField.TEXT2) == null ? "N/A" : parentTask.getCachedValue(TaskField.TEXT2).toString();
                                String functionId = parentTask.getCachedValue(TaskField.TEXT3) == null ? "N/A" : parentTask.getCachedValue(TaskField.TEXT3).toString();
                                String functionName = parentTask.getCachedValue(TaskField.TEXT4) == null ? "N/A" : parentTask.getCachedValue(TaskField.TEXT4).toString();
                                String taskType = parentTask.getCachedValue(TaskField.TEXT5) == null ? "N/A" : parentTask.getCachedValue(TaskField.TEXT5).toString();

                                // add to list
                                TaskModel model = new TaskModel();
                                model.setTaskName(task.getName());
                                model.setParentTaskName(task.getParentTask().getName());
                                model.setTaskId(adminId + "_" + taskId + "_" + functionId);
                                model.setFunctionName(functionName);
                                model.setOrigTaskType(taskType);
                                model.setStartDate(task.getStart());
                                model.setFinishDate(task.getFinish());
                                model.setResourceName(resourceNames);

                                System.out.println("Task ID: " + model.getTaskId());
                                System.out.println("Function name: " + model.getFunctionName());
                                System.out.println("Original task type: " + model.getOrigTaskType());
                                System.out.println("Started at: " + model.getStartDate());
                                System.out.println("Finished at: " + model.getFinishDate());
                                System.out.println("Resources: " + model.getResourceName());

                                tasks.add(model);
                            }
                        }
                    }
                }
            }
        }

        return tasks;
    }

    private ProjectFile getMppFile(String filePath) throws MPXJException {
        ProjectReader reader = new MPPReader();
        return reader.read(filePath);
    }

}
