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
import java.util.Objects;

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
                                if (resourceAssignment.getResource() != null) {     // skip no resource tasks
                                    resourceNames = resourceAssignment.getResource().getName().trim() + ", ";
                                }
                            }

                            resourceNames = resourceNames.length() > 0 ? resourceNames.substring(0, resourceNames.length() - 2) : resourceNames;

                            if (resourceNames.contains(param.getTargetResourceName())) {
                                System.out.println("-----");
                                System.out.println("Found target task: " + task.getName());
                                System.out.println("Parent task: " + task.getParentTask().getName());

                                Task parentTask = task.getParentTask();

                                // copy to model
                                TaskModel model = new TaskModel();

                                // TODO START

                                // get relation of current tasks
                                List<Relation> predecessors = task.getPredecessors();

                                if (predecessors != null && !predecessors.isEmpty()) {

                                    for (Relation relation : predecessors) {

                                        TaskModel previousTask = new TaskModel();

                                        Task preTask = relation.getTargetTask();

                                        previousTask.setTaskName(preTask.getName());

                                        String prevResourceNames = "";

                                        if (preTask.getResourceAssignments() != null) {
                                            for (ResourceAssignment resourceAssignment : preTask.getResourceAssignments()) {
                                                if (resourceAssignment.getResource() != null) {     // skip no resource tasks
                                                    prevResourceNames = resourceAssignment.getResource().getName().trim() + ", ";
                                                }
                                            }

                                            prevResourceNames = prevResourceNames.length() > 0 ? prevResourceNames.substring(0, prevResourceNames.length() - 2) : prevResourceNames;
                                        }

                                        previousTask.setResourceName(prevResourceNames);

                                        previousTask.setFinishDate(preTask.getFinish());
                                        previousTask.setTaskPercentage(preTask.getPercentageComplete());

                                        model.getPreviousTasks().add(previousTask);
                                    }
                                }

                                // TODO END

                                String adminId = parentTask.getCachedValue(TaskField.TEXT1) == null ? "N/A" : parentTask.getCachedValue(TaskField.TEXT1).toString();
                                String taskId = parentTask.getCachedValue(TaskField.TEXT2) == null ? "N/A" : parentTask.getCachedValue(TaskField.TEXT2).toString();
                                String functionId = parentTask.getCachedValue(TaskField.TEXT3) == null ? "N/A" : parentTask.getCachedValue(TaskField.TEXT3).toString();
                                String functionName = parentTask.getCachedValue(TaskField.TEXT4) == null ? "N/A" : parentTask.getCachedValue(TaskField.TEXT4).toString();
                                String taskType = parentTask.getCachedValue(TaskField.TEXT5) == null ? "N/A" : parentTask.getCachedValue(TaskField.TEXT5).toString();

                                // add to list

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
                                for (TaskModel prevTask : model.getPreviousTasks()) {
                                    System.out.println("Prev task name: " + prevTask.getTaskName());
                                    System.out.println("Prev task finished at: " + prevTask.getFinishDate());
                                    System.out.println("Prev task percentage: " + prevTask.getTaskPercentage());
                                    System.out.println("Prev task resources: " + prevTask.getResourceName());
                                }

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
