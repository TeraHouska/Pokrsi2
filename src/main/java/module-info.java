module terahouska.pokrsi2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens terahouska.pokrsi2 to javafx.fxml;
    exports terahouska.pokrsi2;
    exports terahouska.pokrsi2.controller;
    opens terahouska.pokrsi2.controller to javafx.fxml;
}