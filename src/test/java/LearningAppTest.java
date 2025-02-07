import amba.LearningApp;

import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LearningAppTest {

    private LearningApp learningApp;

    @BeforeAll
    static void initJavaFX() {
        JavaFXInitializer.ensureJavaFXToolkitInitialized();
    }


    @BeforeEach
    void setUp() {
        learningApp = Mockito.spy(new LearningApp());
    }

    @Test
    void main() {
        // Test der Main-Methode
        assertDoesNotThrow(() -> LearningApp.main(new String[]{}),
                "Main-Methode sollte ohne Ausnahme ausführbar sein.");
    }

    @Test
    void start() {
        // Arrange
        Stage mockStage = mock(Stage.class);

        // Act & Assert
        assertDoesNotThrow(() -> learningApp.start(mockStage),
                "Start-Methode sollte ohne Ausnahme ausführbar sein.");

        // Verifizieren, dass `start` korrekt aufgerufen wurde
        verify(learningApp, times(1)).start(mockStage);
    }

    @Disabled
    void changeScene() {
        // Arrange
        LearningApp.ScreenState welcomeState = LearningApp.ScreenState.WELCOME_SCREEN_STATE;
        LearningApp.ScreenState mapState = LearningApp.ScreenState.MAP_SCREEN_STATE;

        // Act: Sicherstellen, dass Actions im JavaFX-Thread laufen
        Platform.runLater(() -> {
            // Assert
            assertDoesNotThrow(() -> learningApp.changeScreen(welcomeState),
                    "ChangeScreen sollte Welcome-Screen ohne Ausnahme wechseln.");
            assertDoesNotThrow(() -> learningApp.changeScreen(mapState),
                    "ChangeScreen sollte Map-Screen ohne Ausnahme wechseln.");
        });

        // Verify
        verify(learningApp, times(1)).changeScreen(welcomeState);
        verify(learningApp, times(1)).changeScreen(mapState);
    }

    @Test
    void testEnumScreenState() {
        // Verifiziert die `ScreenState`-Enum-Werte
        assertEquals(LearningApp.ScreenState.WELCOME_SCREEN_STATE,
                LearningApp.ScreenState.valueOf("WELCOME_SCREEN_STATE"),
                "WELCOME_SCREEN_STATE sollte korrekt definiert sein.");
        assertEquals(LearningApp.ScreenState.MAP_SCREEN_STATE,
                LearningApp.ScreenState.valueOf("MAP_SCREEN_STATE"),
                "MAP_SCREEN_STATE sollte korrekt definiert sein.");
        assertEquals(LearningApp.ScreenState.ENEMY_SCREEN_STATE,
                LearningApp.ScreenState.valueOf("ENEMY_SCREEN_STATE"),
                "ENEMY_SCREEN_STATE sollte korrekt definiert sein.");
    }

    @Test
    void testEnemyScreenStateEnum() {
        // Verifiziert die `EnemyScreenState`-Enum-Werte
        assertEquals(LearningApp.EnemyScreenState.NEXT_QUESTION,
                LearningApp.EnemyScreenState.valueOf("NEXT_QUESTION"),
                "NEXT_QUESTION sollte korrekt definiert sein.");
        assertEquals(LearningApp.EnemyScreenState.NO_QUESTIONS,
                LearningApp.EnemyScreenState.valueOf("NO_QUESTIONS"),
                "NO_QUESTIONS sollte korrekt definiert sein.");
        assertEquals(LearningApp.EnemyScreenState.CLEAR_QUESTIONS,
                LearningApp.EnemyScreenState.valueOf("CLEAR_QUESTIONS"),
                "CLEAR_QUESTIONS sollte korrekt definiert sein.");
    }
}