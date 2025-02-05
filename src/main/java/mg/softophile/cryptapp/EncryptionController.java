package mg.softophile.cryptapp;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import mg.softophile.cryptapp.model.Encryption;

public class EncryptionController {
    @FXML
    private TextField firstTF;
    @FXML
    private TextField secondTF;
    @FXML
    private ComboBox<Integer> comboBox;
    @FXML
    private CheckBox checkBox;

    @FXML
    protected void chooseVideo() {
        firstTF.setText(
            CryptApp.chooseFile("Vidéo à chiffrer", "*.mp4")
        );
    }

    @FXML
    protected void chooseDirectory(){
        secondTF.setText(
            CryptApp.chooseDirectory()
        );
    }

    @FXML
    protected void _encrypt() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setContentText(encrypt());
        alert.showAndWait();
    }

    private String encrypt() {
        String result = "";
        if(firstTF.getText().isEmpty() || secondTF.getText().isEmpty() || comboBox.getValue() == null){
             result = "Veuillez remplir correctement les champs!";
        } else {
            result = (Encryption.encrypt(firstTF.getText(),secondTF.getText(),comboBox.getValue(),checkBox.isSelected()));
        }
        clear();
        return result;
    }

    private void clear() {
        firstTF.clear();
        secondTF.clear();
        comboBox.setValue(null);
        checkBox.setSelected(false);
        System.gc();
    }
}
