module mg.softophile.cryptapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens mg.softophile.cryptapp to javafx.fxml;
    exports mg.softophile.cryptapp;
}