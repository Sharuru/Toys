package self.srr.m2gg.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.awt.event.ActionEvent;
import java.io.File;


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
    public void initialize() {

        taskTypeCBox.setItems(FXCollections.observableArrayList("LOCAL", "CHECK(WIP)", "UPDATE"));
        taskTypeCBox.getSelectionModel().selectFirst();
        fileLocationButton.setOnMouseClicked(this::fileChooserEvents);

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


}
