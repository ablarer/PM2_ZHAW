package gruppe01.amba.projekt2.lernapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;

import gruppe01.amba.projekt2.lernapp.LearningApp.ScreenState;

/**
 * Class WelcomeScreenController.
 *
 * @author Alexander Frank
 * @version 09.05.2020
 */
public class WelcomeScreenController {
    @FXML public Button closeButton;
    @FXML public Button startButton;
    @FXML public ChoiceBox<LearningPackage> learningPackagesBox;
    @FXML public Button importButton;
    private GameState gameState;
    private PropertyHandler propertyHandler;
    private ObservableList<LearningPackage> learningPackageList = FXCollections.observableArrayList();

    /**
     * Sets property handler.
     *
     * @param propertyHandler the property handler
     */
    public void setPropertyHandler(PropertyHandler propertyHandler) {
        this.propertyHandler = propertyHandler;
    }

    /**
     * Sets game state.
     *
     * @param gameState the game state
     */
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    /**
     * Method to close application.
     */
    @FXML
    private void closeApplication() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Load packages in drop down.
     */
    @FXML
    public void loadPackagesInDropDown() {
        for(int i = 0; i < gameState.getLearningPackages().size(); i++) {
            learningPackageList.add(gameState.getLearningPackages().get(i));
        }
        learningPackagesBox.setItems(learningPackageList);
        startButton.setOnAction(e -> chooseLearningPackage(learningPackagesBox));
    }

    /**
     * Method to choose learning package.
     */
    @FXML
    private void chooseLearningPackage(ChoiceBox<LearningPackage> learningPackagesBox) {
        LearningPackage learningPackage = learningPackagesBox.getValue();
        if (learningPackage != null) {
            gameState.startLearningPackage(learningPackage.getId());
            startNewGame();
        }
    }

    /**
     * Method to start a new game.
     */
    @FXML
    private void startNewGame() {
        try {
            propertyHandler.setState(ScreenState.MAP_SCREEN_STATE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to import a learning package.
     */
    @FXML
    private void importLearningPackage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Textdokument", "*.txt"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if(selectedFile != null) {
                gameState.importLearningPackage(selectedFile.getPath());
                loadPackagesInDropDown();
        }
    }
}