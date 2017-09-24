package self.srr.m2gg.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import self.srr.m2gg.core.biz.MppBiz;
import self.srr.m2gg.core.model.MppBizParam;
import self.srr.m2gg.core.model.TaskModel;

import java.awt.event.ActionEvent;
import java.io.File;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;


public class Controller {

    @FXML
    public TextArea logTextArea;

    @FXML
    public TextField fileLocationInput;

    @FXML
    public Button fileLocationButton;

    @FXML
    public ChoiceBox taskTypeCBox;

    @FXML
    public Button beginTaskBtn;

    @FXML
    public DatePicker fromDtPicker;

    @FXML
    public DatePicker toDtPicker;

    @FXML
    public TextField resNameInput;


    @FXML
    public void initialize() {

        taskTypeCBox.setItems(FXCollections.observableArrayList("LOCAL", "CHECK(WIP)", "UPDATE"));
        taskTypeCBox.getSelectionModel().selectFirst();
        fileLocationButton.setOnMouseClicked(this::fileChooserEvents);

        beginTaskBtn.setOnMouseClicked(this::beginTaskButtonEvents);


    }


    private void fileChooserEvents(MouseEvent event) {
        logTextArea.appendText("File chooser opened. \n");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose the mpp file");
        fileChooser.setInitialDirectory(new File("F:\\"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("MPP files (*.mpp)", "*.mpp"));
        File mppFileLocation = fileChooser.showOpenDialog(fileLocationButton.getScene().getWindow());
        if (mppFileLocation != null) {
            fileLocationInput.setText(mppFileLocation.getAbsolutePath());
            logTextArea.appendText("File chosen: " + mppFileLocation.getAbsolutePath() + "\n");
        } else {
            fileLocationInput.setText("");
            logTextArea.appendText("File chosen canceled. \n");
        }
    }

    private void beginTaskButtonEvents(MouseEvent event) {
        logTextArea.appendText("Begin task... \n");
        MppBizParam param = new MppBizParam();
        param.setFilePath(fileLocationInput.getText());
        param.setStartDtStr(fromDtPicker.getValue().toString());
        param.setFinishDtStr(toDtPicker.getValue().toString());
        param.setTargetResourceName(resNameInput.getText());

        List<TaskModel> a = new MppBiz().getTasks(param);

        for (TaskModel task : a) {
            logTextArea.appendText("-----" + "\n");
            logTextArea.appendText("Found target task: " + task.getTaskName() + "\n");
            logTextArea.appendText("Parent task: " + task.getParentTaskName() + "\n");
            logTextArea.appendText("Task ID: " + task.getTaskId() + "\n");
            logTextArea.appendText("Function name: " + task.getFunctionName() + "\n");
            logTextArea.appendText("Original task type: " + task.getOrigTaskType() + "\n");
            logTextArea.appendText("Started at: " + task.getStartDate() + "\n");
            logTextArea.appendText("Finished at: " + task.getFinishDate() + "\n");
            logTextArea.appendText("Resources: " + task.getResourceName() + "\n");
            if (!task.getRelyTasks().isEmpty()) {
                logTextArea.appendText("  Have previous tasks: " + "\n");
            }
            for (int i = 0; i < task.getRelyTasks().size(); i++) {
                if (i > 0) {
                    logTextArea.appendText("    -----" + "\n");
                }
                logTextArea.appendText("    Prev task name #" + (i + 1) + ": " + task.getRelyTasks().get(i).getTaskName() + "\n");
                logTextArea.appendText("    Prev task ID: " + task.getTaskId() + "\n");
                logTextArea.appendText("    Prev function name: " + task.getFunctionName() + "\n");
                logTextArea.appendText("    Prev original task type: " + task.getOrigTaskType() + "\n");
                logTextArea.appendText("    Prev task should finished at: " + task.getRelyTasks().get(i).getFinishDate() + "\n");
                logTextArea.appendText("    Prev task percentage: " + task.getRelyTasks().get(i).getTaskPercentage() + "%" + "\n");
                logTextArea.appendText("    Prev task resources: " + task.getRelyTasks().get(i).getResourceName() + "\n");
            }
        }
    }


}
