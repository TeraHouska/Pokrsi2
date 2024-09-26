package terahouska.pokrsi2.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LobbyController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}