package amba;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The type Learning app.
 *
 * @author Alexander Frank, Marc Meyer
 * @version 09.05.2020
 */
public class LearningApp extends Application {
    private Stage primaryStage;
    private PropertyHandler propertyHandler = new PropertyHandler();
    private GameState gameState = new GameState(propertyHandler);
    private final static int WIDTH = 600;
    private final static int HEIGHT = 400;
    private final static String WELCOME_SCREEN = "/WelcomeWindow.fxml";
    private final static String MAP_SCREEN = "/MapScreen.fxml";
    private final static String ENEMY_SCREEN = "/EnemyScreen.fxml";
    private final static String LEARNING_SCREEN = "/LearningScreen.fxml";

    /**
     * Enum for representating screen state for javafx properties.
     */
    public enum ScreenState {
        WELCOME_SCREEN_STATE, MAP_SCREEN_STATE, ENEMY_SCREEN_STATE
    }

    /**
     * Enum for representating enemy screen state for javafx properties.
     */
    public enum EnemyScreenState {
        NEXT_QUESTION, NO_QUESTIONS, CLEAR_QUESTIONS
    }

    /**
     * Mainmethod.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Method to call the gameWindow-method and passe the primaryStage to it.
     *
     * @param primaryStage primaryStage of the application.
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        learningScreen();
    }

    private void learningScreen() {
        try {
            // FXML-Datei laden
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(LEARNING_SCREEN));
            Parent root = fxmlLoader.load();

            // Controller abrufen
            LearningScreenController learningScreenController = fxmlLoader.getController();

            // Controller inicialisieren
            if (learningScreenController != null) {
                learningScreenController.setMainApp(this); // Methode aufrufen, falls definiert
            } else {
                System.err.println("LearningScreenController konnte nicht instanziiert werden.");
            }

            // Scene setzen
            setPrimaryStage(root);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to set up the window of the application and set its width to 600 and height to 400. The FXMLLoader file is
     * the WelcomeWindow.fxml.
     */
    private void welcomeScreen() {
        try {
            // FXML-Datei laden
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(WELCOME_SCREEN));
            Parent root = fxmlLoader.load();

            // Controller holen und initialisieren
            WelcomeScreenController welcomeScreenController = fxmlLoader.getController();
            if (welcomeScreenController != null) {
                welcomeScreenController.setPropertyHandler(propertyHandler);
                welcomeScreenController.setGameState(gameState);

                // `loadPackagesInDropDown` erst aufrufen, wenn der Controller vollständig gesetzt ist
                welcomeScreenController.loadPackagesInDropDown();
            } else {
                System.err.println("Controller konnte nicht initialisiert werden.");
            }

            // PropertyHandler-Listener aktualisieren
            propertyHandler.stateProperty().removeListener(statePropertyListener);
            propertyHandler.stateProperty().addListener(statePropertyListener);

            // Primäre Scene setzen
            setPrimaryStage(root);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to set up the window of the application and set its width to 600 and height to 400. The FXMLLoader file is
     * the WelcomeWindow.fxml.
     */
    private void mapScreen() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(MAP_SCREEN));
            Parent root = fxmlLoader.load();
            MapScreenController mapScreenController = fxmlLoader.getController();
            mapScreenController.setPropertyHandler(propertyHandler);
            mapScreenController.setGameState(gameState);
            mapScreenController.populateEnemies();
            setPrimaryStage(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to set up the window of the application and set its width to 600 and height to 400. The FXMLLoader file is
     * the EnemyScreen.fxml.
     */
    private void enemyScreen() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ENEMY_SCREEN));
            Parent root = fxmlLoader.load();
            EnemyScreenController enemyScreenController = fxmlLoader.getController();
            enemyScreenController.setPropertyHandler(propertyHandler);
            enemyScreenController.setGameState(gameState);
            setPrimaryStage(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to set the primary stage.
     */
    private void setPrimaryStage(Parent root) {
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Method to change the current scene based on the current screenstate.
     *
     * @param state the state
     */
    public void changeScreen(ScreenState state) {
        if (state == null) {
            throw new IllegalArgumentException("ScreenState darf nicht null sein.");
        }

        try {
            // Stelle sicher, dass der Wechsel innerhalb des JavaFX-Anwendungsthreads erfolgt
            if (!Platform.isFxApplicationThread()) {
                Platform.runLater(() -> performScreenChange(state));
            } else {
                performScreenChange(state);
            }
        } catch (Exception e) {
            // Logging für bessere Nachverfolgbarkeit im Fehlerfall
            System.err.println("Fehler beim Wechsel der Szenen: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Szenenwechsel fehlgeschlagen für Zustand: " + state, e);
        }
    }

    private void performScreenChange(ScreenState state) {
        switch (state) {
            case WELCOME_SCREEN_STATE:
                welcomeScreen(); // Methode zum Initialisieren des Welcome-Screens
                break;
            case MAP_SCREEN_STATE:
                mapScreen(); // Methode zum Initialisieren des Map-Screens
                break;
            case ENEMY_SCREEN_STATE:
                enemyScreen(); // Methode zum Initialisieren des Enemy-Screens
                break;
            default:
                throw new IllegalStateException("Unbekannter ScreenState: " + state);
        }
    }

    /**
     * Method to change the current screen state.
     */
    private void stateChanged(ScreenState state) {
        changeScreen(state);
    }

    /**
     * Method for listening to properties
     */
    private final ChangeListener<ScreenState> statePropertyListener = (observable, oldValue, newValue) -> stateChanged(propertyHandler.getState());
}