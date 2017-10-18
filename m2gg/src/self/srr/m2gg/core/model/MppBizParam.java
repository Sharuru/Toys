package self.srr.m2gg.core.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MppBizParam {
    private String filePath;

    private String startDtStr;

    private String finishDtStr;

    private String targetResourceName;

    private List<String> targetTaskIds = new ArrayList<>();

}
