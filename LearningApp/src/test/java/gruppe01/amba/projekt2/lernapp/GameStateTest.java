package gruppe01.amba.projekt2.lernapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class GameStateTest {

    private GameState gameState = new GameState(new PropertyHandler());
    private PackageReader packageReaderTest;
    private PackageHandler packageHandlerTest;

    @Mock
    private LearningPackage learningPackageMock = mock(LearningPackage.class);
    @Mock
    private Enemy enemy1Mock = mock(Enemy.class);
    @Mock
    private Enemy enemy2Mock = mock(Enemy.class);
    @Mock
    private Enemy enemy3Mock = mock(Enemy.class);
    @Mock
    private Enemy enemy4Mock = mock(Enemy.class);
    @Mock
    private Enemy enemy5Mock = mock(Enemy.class);

    HashMap<Integer, Enemy> enemiesArray = new HashMap<>();

    {
        try {
            packageReaderTest = new PackageReader();
            packageHandlerTest = new PackageHandler(packageReaderTest);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void getLearningPackagesTest() {
        GameState gameState = new GameState(new PropertyHandler());
        HashMap<Integer, LearningPackage> learningPackagesExpected = packageHandlerTest.getLearningPackages();
        HashMap<Integer, LearningPackage> learningPackageActual = gameState.getLearningPackages();
        assertEquals(learningPackagesExpected.size(), learningPackageActual.size());
    }

    @Test
    public void getEnemiesTest() {
        fillEnemiesMockInEnemiesArray();
        when(learningPackageMock.getEnemies()).thenReturn(enemiesArray);

        gameState.startLearningPackage(1);
        gameState.startFight(1);

        HashMap<Integer, Enemy> enemyHashMapExpected = learningPackageMock.getEnemies();
        HashMap<Integer, Enemy> enemyHashMapActual = gameState.getEnemies();

        assertEquals(enemyHashMapExpected.size(), enemyHashMapActual.size());
    }

    @Test
    public void getAnswersOfCurrentQuestionTest() {
        ArrayList<String> testAnswers = new ArrayList<>() {{ add("Answer1"); add("answer2"); add("answer3)"); }};

        GameState gameState = new GameState(new PropertyHandler());

        gameState.startLearningPackage(1);
        gameState.startFight(1);

        ArrayList<String> questionsArrayActual = gameState.getAnswersOfCurrentQuestion();

        assertEquals(3, questionsArrayActual.size());
    }

    @Test
    public void getPlayerHealthPointsTest() {
        GameState gameState = new GameState(new PropertyHandler());

        gameState.startLearningPackage(1);
        gameState.startFight(1);

        assertEquals(3, gameState.getPlayerHealthPoints());
    }

    @Test
    public void startLearningPackageTest() {
        GameState gameState = new GameState(new PropertyHandler());

        LearningPackage learningPackageActual = gameState.startLearningPackage(1);

        assertEquals(1, learningPackageActual.getId());
    }

    private GameState instanciateGameStateVariables() {
        GameState gameState = new GameState(new PropertyHandler());
        gameState.startLearningPackage(1);
        gameState.startFight(1);

        return gameState;
    }

    private void fillEnemiesMockInEnemiesArray() {
        enemiesArray.put(1, enemy1Mock);
        enemiesArray.put(2, enemy2Mock);
        enemiesArray.put(3, enemy3Mock);
        enemiesArray.put(4, enemy2Mock);
        enemiesArray.put(5, enemy3Mock);
    }
}