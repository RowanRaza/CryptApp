package mg.softophile.cryptapp;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class CryptApp extends Application {
    private static VBox bodyView = null;
    private static VBox encryptionView = null;
    private static VBox decryptionView = null;
    @Override
    public void init() throws Exception{
        encryptionView = new FXMLLoader(CryptApp.class.getResource("encryption-view.fxml")).load();
        decryptionView = new FXMLLoader(CryptApp.class.getResource("decryption-view.fxml")).load();
        ComboBox<Integer> comboBoxKeySize = (ComboBox<Integer>) (((HBox)encryptionView.getChildren().get(2)).getChildren().get(1));
        ObservableList<Integer> list = FXCollections.observableArrayList();
        list.addAll(128, 192, 256);
        comboBoxKeySize.setItems(list);
    }

    @Override
    public void start(Stage stage) throws Exception {
        VBox parent = new FXMLLoader(CryptApp.class.getResource("view.fxml")).load();
        bodyView = (VBox) parent.getChildren().get(3);
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.setResizable(false);
        setRoot("Chiffrement");
        stage.show();


    }

    public static void setRoot(String rootId) throws Exception {
        try {
            bodyView.getChildren().remove(0);
        }catch (Exception ignored) {}
        if(rootId.equals("Chiffrement")) {
            bodyView.getChildren().add(encryptionView);
        } else {
            bodyView.getChildren().add(decryptionView);
        }
        System.gc();
    }

    public static String chooseFile(String description, String extension) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selectionnez une vidéo");
        String value = "";
        FileChooser.ExtensionFilter fileFilter = new FileChooser.ExtensionFilter(description, extension);
        fileChooser.getExtensionFilters().add(fileFilter);
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        try {
            value = selectedFile.getAbsolutePath();
        }catch (Exception ignored) {}
        return value;
    }

    public static String chooseDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choisissez un répertoire");
        String value = "";
        File selectedDirectory = directoryChooser.showDialog(new Stage());
        try{
           value = selectedDirectory.getAbsolutePath();
        } catch (Exception ignored) {}
        return value;
    }

    public static void main(String[] args) {
        launch();
    }

    public static VBox getView(String viewName){
        VBox element = null;
        if(viewName.equals("encryptionView")){
            element = encryptionView;
        } else if(viewName.equals("decryptionView")) {
            element = decryptionView;
        }
        return element;
    }
}