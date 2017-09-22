package self.srr.m2g.biz;

import net.sf.mpxj.*;
import net.sf.mpxj.mpp.MPPReader;
import net.sf.mpxj.reader.ProjectReader;
import org.springframework.stereotype.Service;
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

    /**
     * get task list of a mpp file
     *
     * @param param filter parameters
     * @return task list of a mpp file
     */
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
        assert project != null;
        assert opStartDt != null;
        assert opEndDt != null;

        for (Task task : project.getAllTasks()) {
            if (task.getName() != null) {   // illegal task check
                if (task.getStart().after(opStartDt) && task.getStart().before(opEndDt)) {  // task time
                    if (task.getOutlineLevel() == 3) {  // only fetch sub tasks
                        if (task.getResourceAssignments() != null) {  // have resources

                            String resourceNames = getResourceStr(task.getResourceAssignments());

                            if (resourceNames.contains(param.getTargetResourceName())) {

                                // copy to model
                                TaskModel model = new TaskModel(task);

                                if (!param.getTargetTaskIds().isEmpty()) {
                                    if (param.getTargetTaskIds().contains(model.getTaskId())) {
                                        tasks.add(trimTask(task));
                                    }
                                } else {
                                    tasks.add(trimTask(task));
                                }
                            }
                        }
                    }
                }
            }
        }

        return tasks;
    }

    /**
     * read and get mpp file
     *
     * @param filePath file path
     * @return mpp file
     * @throws MPXJException mpxj exception
     */
    private ProjectFile getMppFile(String filePath) throws MPXJException {
        ProjectReader reader = new MPPReader();
        return reader.read(filePath);
    }

    /**
     * generate resources string of a task
     *
     * @param resAssigns resource assignment of a task
     * @return generated resources string, split by comma
     */
    public static String getResourceStr(List<ResourceAssignment> resAssigns) {

        String resStr = "";

        for (ResourceAssignment aAssign : resAssigns) {
            if (aAssign.getResource() != null) {     // skip no resource tasks
                resStr = aAssign.getResource().getName().trim() + ", ";
            }
        }

        return resStr.length() > 0 ? resStr.substring(0, resStr.length() - 2) : resStr;
    }

    /**
     * Trim mpp task model
     *
     * @param task task
     * @return trimmed task model
     */
    private TaskModel trimTask(Task task) {

        // copy model
        TaskModel model = new TaskModel(task);

        System.out.println("-----");
        System.out.println("Found target task: " + model.getTaskName());
        System.out.println("Parent task: " + model.getParentTaskName());


        // get relation of current tasks
        List<Relation> predecessors = task.getPredecessors();

        // add association task to list
        if (predecessors != null && !predecessors.isEmpty()) {
            for (Relation relation : predecessors) {
                model.getRelyTasks().add(new TaskModel(relation.getTargetTask()));
            }
        }

        System.out.println("Task ID: " + model.getTaskId());
        System.out.println("Function name: " + model.getFunctionName());
        System.out.println("Original task type: " + model.getOrigTaskType());
        System.out.println("Started at: " + model.getStartDate());
        System.out.println("Finished at: " + model.getFinishDate());
        System.out.println("Resources: " + model.getResourceName());
        if (!model.getRelyTasks().isEmpty()) {
            System.out.println("  Have previous tasks: ");
        }
        for (int i = 0; i < model.getRelyTasks().size(); i++) {
            if (i > 0) {
                System.out.println("    -----");
            }
            System.out.println("    Prev task name #" + (i + 1) + ": " + model.getRelyTasks().get(i).getTaskName());
            System.out.println("    Prev task ID: " + model.getTaskId());
            System.out.println("    Prev function name: " + model.getFunctionName());
            System.out.println("    Prev original task type: " + model.getOrigTaskType());
            System.out.println("    Prev task should finished at: " + model.getRelyTasks().get(i).getFinishDate());
            System.out.println("    Prev task percentage: " + model.getRelyTasks().get(i).getTaskPercentage() + "%");
            System.out.println("    Prev task resources: " + model.getRelyTasks().get(i).getResourceName());
        }

        return model;
    }

}
