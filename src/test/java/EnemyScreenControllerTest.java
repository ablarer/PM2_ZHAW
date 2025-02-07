import amba.EnemyScreenController;
import amba.GameState;
import amba.LearningApp;
import amba.PropertyHandler;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class EnemyScreenControllerTest {

    private EnemyScreenController controller;
    private GameState mockGameState;
    private PropertyHandler mockPropertyHandler;

    @BeforeAll
    static void initJavaFX() {
        JavaFXInitializer.ensureJavaFXToolkitInitialized();
    }

    @BeforeEach
    void setUp() {
        // Controller und Mocks initialisieren
        controller = new EnemyScreenController();
        mockGameState = createMockGameState();
        mockPropertyHandler = createMockPropertyHandler();

        // JavaFX-Komponenten vorbereiten
        setupJavaFXComponents();

        // Abhängigkeiten einfügen
        controller.setGameState(mockGameState);
        controller.setPropertyHandler(mockPropertyHandler);
    }

    /**
     * Hilfsmethode zum Erstellen eines GameState-Mocks.
     */
    private GameState createMockGameState() {
        GameState mockState = Mockito.mock(GameState.class);
        when(mockState.getCurrentQuestionFromEnemy()).thenReturn("Mock Question");
        when(mockState.getAnswersOfCurrentQuestion()).thenReturn(new ArrayList<>(List.of("Answer 1", "Answer 2", "Answer 3")));
        return mockState;
    }

    /**
     * Hilfsmethode zum Erstellen eines PropertyHandler-Mocks.
     */
    private PropertyHandler createMockPropertyHandler() {
        PropertyHandler mockHandler = Mockito.mock(PropertyHandler.class);
        when(mockHandler.enemyScreenStateProperty()).thenReturn(
                new SimpleObjectProperty<>(LearningApp.EnemyScreenState.NEXT_QUESTION)
        );
        when(mockHandler.getEnemyScreenState()).thenReturn(LearningApp.EnemyScreenState.NEXT_QUESTION);
        return mockHandler;
    }

    /**
     * Hilfsmethode für die Initialisierung von JavaFX-Komponenten.
     */
    private void setupJavaFXComponents() {
        controller.questionBox = new Label();
        controller.answerButtonBox = new HBox(); // Für Antworten
    }

    /**
     * Test: `setPropertyHandler()` sollte den PropertyHandler korrekt setzen und einen Listener registrieren.
     */
    @Test
    void setPropertyHandler_ShouldSetPropertyHandlerAndRegisterListener() {
        // Arrange
        PropertyHandler newMockHandler = Mockito.mock(PropertyHandler.class);
        SimpleObjectProperty<LearningApp.EnemyScreenState> mockProperty =
                new SimpleObjectProperty<>(LearningApp.EnemyScreenState.NEXT_QUESTION);

        // Konfiguriere Mock, um eine gültige Property zurückzugeben
        when(newMockHandler.enemyScreenStateProperty()).thenReturn(mockProperty);

        // Act
        controller.setPropertyHandler(newMockHandler);

        // Assert
        assertEquals(newMockHandler, controller.propertyHandler,
                "The PropertyHandler should be correctly set in the controller.");
        verify(newMockHandler, times(1)).enemyScreenStateProperty();
    }

    /**
     * Test: `populateQuestionButton()` sollte den richtigen Text auf Basis des GameState setzen.
     */
    @Test
    void populateQuestionButton_ShouldSetQuestionTextBasedOnGameState() {
        // Arrange
        String expectedQuestion = "What is 2 + 2?";
        when(mockPropertyHandler.getEnemyScreenState()).thenReturn(LearningApp.EnemyScreenState.NEXT_QUESTION);
        when(mockGameState.getCurrentQuestionFromEnemy()).thenReturn(expectedQuestion);

        // Act
        controller.populateQuestionButton();

        // Assert
        assertEquals(expectedQuestion, controller.questionBox.getText(),
                "The question box should display the current question.");
    }

    /**
     * Test: `populateAnswerButtons()` sollte die passenden Antwort-Buttons dynamisch erstellen.
     */
    @Test
    void populateAnswerButtons_ShouldCreateAnswerButtonsDynamically() {
        // Arrange
        when(mockPropertyHandler.getEnemyScreenState()).thenReturn(LearningApp.EnemyScreenState.NEXT_QUESTION);

        // Act
        controller.populateAnswerButtons();

        // Assert
        assertEquals(3, controller.answerButtonBox.getChildren().size(),
                "Three answer buttons should be created.");
        assertTrue(controller.answerButtonBox.getChildren().get(0) instanceof Button,
                "The first element in the answer button box should be a Button.");
        assertEquals("Answer 1", ((Button) controller.answerButtonBox.getChildren().get(0)).getText(),
                "The first button should display 'Answer 1'.");
    }

    /**
     * Test: `setGameState()` sollte den GameState korrekt setzen.
     */
    @Test
    void setGameState_ShouldSetGameState() {
        // Arrange
        GameState newMockGameState = Mockito.mock(GameState.class);

        // Act
        controller.setGameState(newMockGameState);

        // Assert
        assertEquals(newMockGameState, controller.gameState,
                "The GameState should be correctly set in the controller.");
    }
}