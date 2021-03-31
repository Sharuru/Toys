package me.sharuru.ldt.core.common;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Model class for holding the task and the log info
 */
@Data
@NoArgsConstructor
public class MetaTaskInfo {

    /**
     * The information part of the log text
     */
    private String logText;

    /**
     * Which the log text belonging to(test suites or test cases)
     */
    private String type;

    /**
     * If the test task is fully completed without error
     */
    private boolean isFullyCompleted = false;

    /**
     * The path of the log file(used for location)
     */
    private String logPath;

    /**
     * The line no of the log text(used for location)
     */
    private long logLineNo;

    /**
     * The identifier for determined which the task is it is, usually it equals the path of the test sources
     */
    private String taskIdentifier;

    /**
     * When the task is started
     */
    private LocalDateTime startedTime;

    /**
     * When the task is finished
     */
    private LocalDateTime finishedTime;

    /**
     * The child task infos(usually the test cases)
     */
    private List<MetaTaskInfo> childTasks = new ArrayList<>(0);

    /**
     * Get the execution time of the task
     */
    public long getExecutionTime() {
        if (this.startedTime != null && this.finishedTime != null) {
            return Duration.between(this.startedTime, this.finishedTime).getSeconds();
        } else {
            return 0L;
        }
    }

    /**
     * Get the total execution time of the task(usually called in test suites)
     */
    public long getTotalExecutionTime() {
        return this.childTasks.stream().collect(Collectors.summarizingLong(MetaTaskInfo::getExecutionTime)).getSum();
    }
}
