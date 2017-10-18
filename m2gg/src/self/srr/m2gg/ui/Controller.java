package self.srr.m2gg.ui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import self.srr.m2gg.core.biz.MppBiz;
import self.srr.m2gg.core.model.MppBizParam;
import self.srr.m2gg.core.model.TaskModel;

import java.io.File;
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
    public Button copyClipboardBtn;

    @FXML
    public Button clearLogBtn;


    @FXML
    public void initialize() {

        taskTypeCBox.setItems(FXCollections.observableArrayList("LOCAL", "CHECK(WIP)", "UPDATE"));
        taskTypeCBox.getSelectionModel().selectFirst();
        fileLocationButton.setOnMouseClicked(this::fileChooserEvents);

        beginTaskBtn.setOnMouseClicked(this::beginTaskButtonEvents);
        copyClipboardBtn.setOnMouseClicked(this::copyClipboardButtonEvents);
        clearLogBtn.setOnMouseClicked(this::clearLogButtonEvents);


    }


    private void fileChooserEvents(MouseEvent event) {
        logTextArea.appendText("File chooser opened. \n");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose the mpp file");
        fileChooser.setInitialDirectory(new File("D:\\"));
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

        List<TaskModel> tasks = new MppBiz().getTasks(param);

        for (TaskModel aTask : tasks) {
            logTextArea.appendText("-----" + "\n");
            logTextArea.appendText("Found target task: " + aTask.getTaskName() + "\n");
            logTextArea.appendText("Parent task: " + aTask.getParentTaskName() + "\n");
            logTextArea.appendText("Task ID: " + aTask.getTaskId() + "\n");
            logTextArea.appendText("Function name: " + aTask.getFunctionName() + "\n");
            logTextArea.appendText("Original task type: " + aTask.getOrigTaskType() + "\n");
            logTextArea.appendText("Started at: " + aTask.getStartDate() + "\n");
            logTextArea.appendText("Finished at: " + aTask.getFinishDate() + "\n");
            logTextArea.appendText("Resources: " + aTask.getResourceName() + "\n");
            if (!aTask.getRelyTasks().isEmpty()) {
                logTextArea.appendText("  Have previous tasks: " + "\n");
            }
            for (int i = 0; i < aTask.getRelyTasks().size(); i++) {
                if (i > 0) {
                    logTextArea.appendText("    -----" + "\n");
                }
                logTextArea.appendText("    Prev task name #" + (i + 1) + ": " + aTask.getRelyTasks().get(i).getTaskName() + "\n");
                logTextArea.appendText("    Prev task ID: " + aTask.getTaskId() + "\n");
                logTextArea.appendText("    Prev function name: " + aTask.getFunctionName() + "\n");
                logTextArea.appendText("    Prev original task type: " + aTask.getOrigTaskType() + "\n");
                logTextArea.appendText("    Prev task should finished at: " + aTask.getRelyTasks().get(i).getFinishDate() + "\n");
                logTextArea.appendText("    Prev task percentage: " + aTask.getRelyTasks().get(i).getTaskPercentage() + "%" + "\n");
                logTextArea.appendText("    Prev task resources: " + aTask.getRelyTasks().get(i).getResourceName() + "\n");
            }
        }

        logTextArea.appendText("-----" + "\n");
        logTextArea.appendText("Task finished!" + "\n");
        logTextArea.appendText("Filtered: " + tasks.size() + " task(s). \n");
    }

    private void copyClipboardButtonEvents(MouseEvent event) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();

        content.putString(logTextArea.getText());

        clipboard.setContent(content);

        logTextArea.appendText("Log copied!" + "\n");
    }

    private void clearLogButtonEvents(MouseEvent event) {
        logTextArea.setText("");
        logTextArea.appendText("Log cleared!" + "\n");
    }


}
