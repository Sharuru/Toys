package self.srr.m2g.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    private List<TaskModel> previousTasks = new ArrayList<>();
}
