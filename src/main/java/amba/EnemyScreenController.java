package amba;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import java.util.ArrayList;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * The type Enemy screen controller.
 */
public class EnemyScreenController {
    public GameState gameState;
    public PropertyHandler propertyHandler;
    private final static int RADIUS = 2;
    private final static int SPACING = 2;
    private final static Color BACKGROUND_COLOR = Color.LIGHTBLUE;
    private  final static Insets INSET = Insets.EMPTY;

    @FXML
    public Label questionBox;
    @FXML
    public Label enemyHealth;
    @FXML
    public Label playerHealth;
    @FXML
    public HBox answerButtonBox;
    @FXML
    public Text speechBubble;
    @FXML public Button returnButton;
    @FXML public ImageView enemyImageView;


    /**
     * Sets property handler.
     *
     * @param propertyHandler the property handler
     */
    public void setPropertyHandler(PropertyHandler propertyHandler) {
        if (propertyHandler == null) {
            throw new IllegalArgumentException("PropertyHandler darf nicht null sein.");
        }

        this.propertyHandler = propertyHandler;

        // Überprüfen, ob die enemyScreenStateProperty vorhanden ist
        var enemyScreenStateProp = propertyHandler.enemyScreenStateProperty();
        if (enemyScreenStateProp == null) {
            throw new IllegalStateException("enemyScreenStateProperty darf nicht null sein.");
        }

        // Überprüfen, ob der Listener korrekt definiert ist
        if (enemyScreenStatePropertyListener == null) {
            throw new IllegalStateException("enemyScreenStatePropertyListener ist nicht initialisiert.");
        }

        // Listener entfernen und hinzfügen
        enemyScreenStateProp.removeListener(enemyScreenStatePropertyListener);
        enemyScreenStateProp.addListener(enemyScreenStatePropertyListener);
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
     * Back to home.
     */
    @FXML
    private void backToHome() {
        try {
            propertyHandler.setState(LearningApp.ScreenState.MAP_SCREEN_STATE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Populate question button.
     */
    @FXML
    public void populateQuestionButton() {
        if (questionBox == null) {
            throw new IllegalStateException("questionBox wurde nicht initialisiert.");
        }
        if (propertyHandler == null || gameState == null) {
            throw new IllegalStateException("propertyHandler oder gameState wurden nicht gesetzt.");
        }

        questionBox.setText(""); // Standardwert setzen
        questionBox.setBackground(new Background(new BackgroundFill(BACKGROUND_COLOR, new CornerRadii(RADIUS), INSET)));

        // Prüfe den aktuellen Zustand
        LearningApp.EnemyScreenState state = propertyHandler.getEnemyScreenState();
        if (state == null || state.equals(LearningApp.EnemyScreenState.NEXT_QUESTION)) {
            String question = gameState.getCurrentQuestionFromEnemy();
            questionBox.setText(question != null ? question : "N/A");
        } else if (state.equals(LearningApp.EnemyScreenState.NO_QUESTIONS)) {
            questionBox.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, new CornerRadii(RADIUS), INSET)));
        }
    }

    /**
     * Populate answer buttons.
     */
    @FXML
    public void populateAnswerButtons() {
        ObservableList<Node> buttons = answerButtonBox.getChildren();
        answerButtonBox.getChildren().removeAll(buttons);
        if (propertyHandler.getEnemyScreenState().equals(LearningApp.EnemyScreenState.NEXT_QUESTION) || propertyHandler.getEnemyScreenState() == null) {
            ArrayList<String> answers = gameState.getAnswersOfCurrentQuestion();
            for(String currentAnswer : answers) {
                Button newButton = new Button();
                newButton.setBackground(new Background(new BackgroundFill(BACKGROUND_COLOR, new CornerRadii(RADIUS), INSET)));
                double dynamicButtonWidth = (questionBox.getWidth()/answers.size()) - ((answers.size() - 1) * SPACING);
                newButton.setPrefWidth(dynamicButtonWidth);
                newButton.setText(currentAnswer);
                newButton.setOnAction(event -> checkAnswer(((Button) event.getSource()).getText()));
                answerButtonBox.getChildren().add(newButton);
            }
        }
    }

    /**
     * Method to display the enemy avatar in the enemy screen.
     */
    @FXML
    private void displayEnemyAvatar(){
        enemyImageView.setImage(gameState.getCurrentEnemy().getAvatar());
    }

    /**
     * Method to display the healthbar of the enemy and the helathbar of the player on the enemy screen.
     */
    @FXML
    private void displayHealthBar(){
       enemyHealth.setBackground(new Background(new BackgroundFill(BACKGROUND_COLOR, new CornerRadii(RADIUS), INSET)));
       playerHealth.setBackground(new Background(new BackgroundFill(BACKGROUND_COLOR, new CornerRadii(RADIUS), INSET)));
       enemyHealth.setText(gameState.getCurrentEnemy().getName() + " Leben: " + gameState.getCurrentEnemy().getHealthPoints());
       playerHealth.setText("Eigenes Leben: " + gameState.getPlayerHealthPoints());
    }

    /**
     * Method to populate the speechbubble in the enemy screen with a messasge from the enemy.
     */
    @FXML
    private void populateSpeechBubble() {
        speechBubble.setText(gameState.getCurrentEnemy().getMessage());
    }

    /**
     * Method to evaluate the answer selected in the enemy screen.
     */
    @FXML
    private void checkAnswer(String answer) {
        gameState.checkAnswer(answer);
    }

    /**
     * Method for listening to properties
     */
    private final ChangeListener<LearningApp.EnemyScreenState> enemyScreenStatePropertyListener = new ChangeListener<>() {
        @Override
        public void changed(ObservableValue<? extends LearningApp.EnemyScreenState> observable, LearningApp.EnemyScreenState oldValue, LearningApp.EnemyScreenState newValue) {
            if (propertyHandler.getEnemyScreenState().equals(LearningApp.EnemyScreenState.NEXT_QUESTION) || propertyHandler.getEnemyScreenState().equals(LearningApp.EnemyScreenState.NO_QUESTIONS)) {
                populateQuestionButton();
                populateAnswerButtons();
                populateSpeechBubble();
                displayEnemyAvatar();
                displayHealthBar();
            }
        }
    };
}