package self.srr.m2g.model;

import lombok.Data;
import net.sf.mpxj.TaskField;
import net.sf.mpxj.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static self.srr.m2g.biz.MppBiz.getResourceStr;

/**
 * Created by Sharuru on 2017/08/08.
 */
@Data
public class TaskModel {

    private String taskName;

    private String parentTaskName;

    private String taskId;

    private String functionName;

    private String origTaskType;

    private Date startDate;

    private Date finishDate;

    private String resourceName;

    private Number taskPercentage;

    private List<TaskModel> relyTasks = new ArrayList<>();

    public TaskModel() {

    }

    public TaskModel(Task task) {

        Task parentTask = task.getParentTask();

        String parentTaskName = "N/A";
        String adminId = "N/A";
        String taskId = "N/A";
        String functionId = "N/A";
        String functionName = "N/A";
        String originTaskType = "N/A";

        // null field trim
        if (parentTask != null) {
            parentTaskName = parentTask.getName();
            adminId = parentTask.getCachedValue(TaskField.TEXT1) == null ? "N/A" : parentTask.getCachedValue(TaskField.TEXT1).toString();
            taskId = parentTask.getCachedValue(TaskField.TEXT2) == null ? "N/A" : parentTask.getCachedValue(TaskField.TEXT2).toString();
            functionId = parentTask.getCachedValue(TaskField.TEXT3) == null ? "N/A" : parentTask.getCachedValue(TaskField.TEXT3).toString();
            functionName = parentTask.getCachedValue(TaskField.TEXT4) == null ? "N/A" : parentTask.getCachedValue(TaskField.TEXT4).toString();
            originTaskType = parentTask.getCachedValue(TaskField.TEXT5) == null ? "N/A" : parentTask.getCachedValue(TaskField.TEXT5).toString();
        }

        // copy to model
        this.taskName = task.getName();
        this.parentTaskName = parentTaskName;
        this.taskId = adminId + "_" + taskId + "_" + functionId;
        this.functionName = functionName;
        this.origTaskType = originTaskType;
        this.startDate = task.getStart();
        this.finishDate = task.getFinish();
        this.taskPercentage = task.getPercentageComplete();
        this.resourceName = task.getResourceAssignments() == null ? "N/A" : getResourceStr(task.getResourceAssignments());
    }
}
