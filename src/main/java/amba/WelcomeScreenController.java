package amba;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import amba.LearningApp.ScreenState;

/**
 * Controller-Klasse für den Welcome Screen.
 *
 * @author Alexander Frank
 * @version 09.05.2020
 */
public class WelcomeScreenController {
    @FXML private Button closeButton;
    @FXML private Button startButton;
    @FXML private Button importButton;
    @FXML private ChoiceBox<LearningPackage> learningPackagesBox;

    private GameState gameState;
    private PropertyHandler propertyHandler;
    private ObservableList<LearningPackage> learningPackageList = FXCollections.observableArrayList();

    /**
     * Stellt den Property-Handler ein.
     *
     * @param propertyHandler Der Property-Handler.
     */
    public void setPropertyHandler(PropertyHandler propertyHandler) {
        this.propertyHandler = propertyHandler;
    }

    /**
     * Setzt den GameState.
     *
     * @param gameState Der aktuelle GameState.
     */
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    /**
     * Schliesst die Anwendung.
     */
    @FXML
    private void closeApplication() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Lädt die verfügbaren Lernpakete in die ChoiceBox und wählt den ersten Eintrag aus.
     */
    @FXML
    public void loadPackagesInDropDown() {
        // Sicherstellen, dass die Liste leer ist, bevor neue Elemente hinzugefügt werden
        learningPackageList.clear();

        // Überprüfen, ob gameState und LearningPackages korrekt initialisiert sind
        if (gameState != null && gameState.getLearningPackages() != null) {
            // Werte der HashMap (LearningPackages) in die ObservableList übernehmen
            learningPackageList.addAll(gameState.getLearningPackages().values());
            learningPackagesBox.setItems(learningPackageList);

            // Wählt den ersten Eintrag, falls die Liste nicht leer ist
            if (!learningPackageList.isEmpty()) {
                learningPackagesBox.setValue(learningPackageList.get(0)); // Erster Eintrag
            }
        } else {
            System.err.println("GameState oder LearningPackages sind nicht initialisiert.");
        }

        // Setzt die Aktion für den Start-Button, wenn Auswahl erfolgt
        startButton.setOnAction(e -> chooseLearningPackage(learningPackagesBox));
    }

    /**
     * Wählt ein Lernpaket aus und startet es.
     */
    @FXML
    private void chooseLearningPackage(ChoiceBox<LearningPackage> learningPackagesBox) {
        LearningPackage learningPackage = learningPackagesBox.getValue();
        if (learningPackage != null) {
            gameState.startLearningPackage(learningPackage.getId());
            startNewGame();
        } else {
            System.err.println("Kein Lernpaket ausgewählt.");
        }
    }

    /**
     * Startet ein neues Spiel und wechselt die Ansicht.
     */
    @FXML
    private void startNewGame() {
        try {
            if (propertyHandler != null) {
                propertyHandler.setState(ScreenState.MAP_SCREEN_STATE);
            } else {
                System.err.println("PropertyHandler wurde nicht gesetzt.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Importiert ein neues Lernpaket über den File Chooser.
     */
    @FXML
    private void importLearningPackage() {
        try {
            // Create a FileChooser instance
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Textdokument", "*.txt")
            );

            // Get the current Stage (use the button to retrieve the scene window)
            Stage stage = (Stage) importButton.getScene().getWindow();

            // Show the open dialog
            File selectedFile = fileChooser.showOpenDialog(stage);

            // Process selected file
            if (selectedFile != null) {
                // Import the learning package (game logic)
                gameState.importLearningPackage(selectedFile.getPath());
                loadPackagesInDropDown();
            } else {
                System.out.println("No file selected.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error while trying to load a learning package.");
        }
    }
}