package mg.softophile.cryptapp;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import mg.softophile.cryptapp.model.Decryption;

public class DecryptionController {
    @FXML
    private TextField firstTF;
    @FXML
    private TextField secondTF;
    @FXML
    private TextField thirdTF;
    @FXML
    private CheckBox checkBox;

    @FXML
    protected void chooseVideo() {
        firstTF.setText(
            CryptApp.chooseFile("Vidéo à déchiffrer", "*.enc")
        );
    }

    @FXML
    protected void chooseDirectory(){
       secondTF.setText(
            CryptApp.chooseDirectory()
       );
    }

    @FXML
    protected void chooseKey(){
        thirdTF.setText(
            CryptApp.chooseFile("La clé correspondante", "*.key")
        );
    }

    @FXML
    protected void _decrypt() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setContentText(decrypt());
        alert.showAndWait();
    }
    private String decrypt() {
        String result = "";
        if(firstTF.getText().isEmpty() || secondTF.getText().isEmpty() || thirdTF.getText().isEmpty()){
            result = ("Veuillez remplir correctement les champs!");
        } else {
            result = (Decryption.decrypt(firstTF.getText(),secondTF.getText(),thirdTF.getText(),checkBox.isSelected()));
        }
        clear();
        return result;

    }

    private void clear() {
        firstTF.clear();
        secondTF.clear();
        thirdTF.clear();
        checkBox.setSelected(false);
        System.gc();
    }
}
