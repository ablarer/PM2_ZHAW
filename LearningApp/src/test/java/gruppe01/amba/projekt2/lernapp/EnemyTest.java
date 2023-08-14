package gruppe01.amba.projekt2.lernapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class EnemyTest {
    Enemy testEnemy;
    private final String DEAD_IMAGE_NAME = "dead";
    private final String ALIVE_IMAGE_NAME = "alive";
    private final String QUESTION_ONE_NAME = "1";
    private final String QUESTION_TWO_NAME = "2";

    @BeforeEach
    void setUp() {
        Image testImageDeadMock = mock(Image.class);
        Image testImageAliveMock = mock(Image.class);
        when(testImageDeadMock.toString()).thenReturn(DEAD_IMAGE_NAME);
        when(testImageAliveMock.toString()).thenReturn(ALIVE_IMAGE_NAME);
        Map<String, Image> avatarMap = new HashMap<>();
        avatarMap.put(DEAD_IMAGE_NAME, testImageDeadMock);
        avatarMap.put(ALIVE_IMAGE_NAME, testImageAliveMock);
        Question questionOneMock = mock(Question.class);
        Question questionTwoMock = mock(Question.class);
        when(questionOneMock.getQuestion()).thenReturn(QUESTION_ONE_NAME);
        when(questionTwoMock.getQuestion()).thenReturn(QUESTION_TWO_NAME);
        List<Question> questions = new ArrayList<>();
        questions.add(questionOneMock);
        questions.add(questionTwoMock);
        testEnemy = new Enemy(0,"TestDescription", 0, "Test Enemy Name",avatarMap, questions);
    }

    /**
     * Verifies that the avatar image for an alive enemy is shown by default after creating an enemy.
     * Verifies that the avatar image for a dead enemy is shown after enemy has died and it's render() method is called
     */
    @Test
    void testAvatar() {
        String avatarImageName = testEnemy.getAvatar().toString();
        assertEquals(ALIVE_IMAGE_NAME, avatarImageName);
        testEnemy.die();
        testEnemy.render();
        avatarImageName = testEnemy.getAvatar().toString();
        assertEquals(DEAD_IMAGE_NAME, avatarImageName);
    }

    /**
     * Verifies that the first question in the list of questions passed to the constructor of Enemy is returned the first time when calling getCurrentQuestion()
     */
    @Test
    void getCurrentQuestion() {
        assertEquals(QUESTION_ONE_NAME, testEnemy.getCurrentQuestion().getQuestion());
    }

    /**
     * Verifies that the second question in the list of questions passed to the constructor of Enemy is returned the first time when calling getNextQuestion()
     * Verifies that getNextQuestion() returns the first question in the list of questions, when the current question is the last question in the list
     */
    @Test
    void getNextQuestion() {
        assertEquals(QUESTION_TWO_NAME, testEnemy.getNextQuestion().getQuestion());
        assertEquals(QUESTION_ONE_NAME, testEnemy.getNextQuestion().getQuestion());
    }

    /**
     * Verifies that getMessage() returns the MessageType which has been passed to method talk() before
     */
    @Test
    void testTalk() {
        testEnemy.talk(MessageType.WELCOME_MESSAGE);
        assertEquals(MessageType.WELCOME_MESSAGE.getMessage(), testEnemy.getMessage());
    }

    /**
     * Verifies that the correct number of health points is returned after calling increaseHealth()
     */
    @Test
    void increaseHealth() {
        testEnemy.setHealthPoints(5);
        testEnemy.increaseHealth();
        assertEquals(6, testEnemy.getHealthPoints());
    }

    /**
     * Verifies that the correct number of health points is returned after calling decreasehealth()
     */
    @Test
    void decreasehealth() {
        testEnemy.setHealthPoints(5);
        testEnemy.decreasehealth();
        assertEquals(4, testEnemy.getHealthPoints());
    }

    /**
     * Verifies that isCharacterAlive() returns true, after an Enemy object has been instantiated
     * Verifies that isCharacterAlive() returns false, after after calling the die() method
     */
    @Test
    void die() {
        assertEquals(true, testEnemy.isCharacterAlive());
        testEnemy.die();
        assertEquals(false, testEnemy.isCharacterAlive());
    }
}