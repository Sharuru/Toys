package me.sharuru.ldt.core.common;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class LogModel {

    private String rawText;

    private String logText;

    private String type;

    private String tid;
    
    private LocalDateTime startedTime;

    private LocalDateTime finishedTime;

    private List<LogModel> childLogModels = new ArrayList<>(0);
}
