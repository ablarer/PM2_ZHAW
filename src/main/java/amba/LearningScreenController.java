package amba;

import javafx.application.Platform;
import javafx.fxml.FXML;

/**
 * The type LearningScreenController.
 *
 * @author Albert Blarer
 * @version 23.01.25
 */

public class LearningScreenController {

    private LearningApp mainApp; // Referenz auf die Hauptanwendung

    public void setMainApp(LearningApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    public void initialize() {
        // Verzögerung von 3 Sekunden, bevor zur WelcomeScreen gewechselt wird
        Platform.runLater(() -> {
            new Thread(() -> {
                try {
                    Thread.sleep(3000); // Warten für 3000 Millisekunden
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(this::switchToWelcomeScreen);
            }).start();
        });
    }

    private void switchToWelcomeScreen() {
        if (mainApp != null) {
            // Wechsel über LearningApp steuern
            mainApp.changeScreen(LearningApp.ScreenState.WELCOME_SCREEN_STATE);
        } else {
            System.err.println("Keine Referenz zur Hauptanwendung gesetzt!");
        }
    }
}