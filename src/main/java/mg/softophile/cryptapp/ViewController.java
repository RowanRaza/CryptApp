package mg.softophile.cryptapp;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class ViewController {
    @FXML
    protected void switchRoot(MouseEvent event) throws Exception {
        Button button = (Button) event.getSource();
        CryptApp.setRoot(button.getText());
        button = null;
        System.gc();
    }
    @FXML
    protected void quit() {
        Platform.exit();
    }
}