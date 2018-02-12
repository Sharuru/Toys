package self.srr.svnlocker.ui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import org.tmatesoft.svn.core.wc.SVNClientManager;


import java.io.File;

public class Controller {

    @FXML
    public TextField filePathText;

    @FXML
    public Button fileChooserButton;

    @FXML
    public Button lockButton;

    @FXML
    public TextArea logTextArea;

    @FXML
    public Label statusText;

    @FXML
    public void initialize() {
        fileChooserButton.setOnMouseClicked(this::fileChooserEvents);
        lockButton.setOnMouseClicked(this::lockButtonEvents);
    }

    private void fileChooserEvents(MouseEvent event) {
        logTextArea.appendText("File chooser opened. \n");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose the target file");
        fileChooser.setInitialDirectory(new File("C:\\"));
        File fileLocation = fileChooser.showOpenDialog(fileChooserButton.getScene().getWindow());
        if (fileLocation != null) {
            filePathText.setText(fileLocation.getAbsolutePath());
            logTextArea.appendText("File chosen: " + fileLocation.getAbsolutePath() + "\n");
        } else {
            filePathText.setText("");
            logTextArea.appendText("File chosen canceled. \n");
        }
    }

    private void lockButtonEvents(MouseEvent event) {
        logTextArea.appendText("Begin task... \n");
        statusText.setText("Try locking: " + filePathText.getText().substring(filePathText.getText().lastIndexOf("\\") + 1));
        try {
            //SVNClientManager.newInstance().getWCClient().doLock(new File[]{new File(filePathText.getText())}, false, "Lock by ");
            statusText.setText("Locked by: " + SVNClientManager.newInstance().getStatusClient().doStatus(new File(filePathText.getText()), false));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
